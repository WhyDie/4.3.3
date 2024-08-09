package ua.whydie;
import java.util.ArrayList;
import java.util.List;

public class SeparateChaining<K, V> {
    private List<HashNode<K, V>> buckets;
    private int capacity;
    private int countOfItems;

    public SeparateChaining() {
        this.capacity = 10; // начальная емкость
        this.buckets = new ArrayList<>(capacity);
        this.countOfItems = 0;

        for (int i = 0; i < capacity; i++) {
            buckets.add(null);
        }
    }

    public int size() {
        return countOfItems;
    }

    public boolean isEmpty() {
        return countOfItems == 0;
    }

    private int hash(K key) {
        return key.hashCode();
    }

    private int getBucketIndex(K key) {
        int hashCode = hash(key);
        return (hashCode & 0x7FFFFFFF) % capacity;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = buckets.get(bucketIndex);

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }

        return null;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key);
        HashNode<K, V> head = buckets.get(bucketIndex);

        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        countOfItems++;
        head = buckets.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<>(key, value, hashCode);
        newNode.next = head;
        buckets.set(bucketIndex, newNode);

        if ((1.0 * countOfItems) / capacity >= 0.7) {
            resize();
        }
    }

    public void delete(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = buckets.get(bucketIndex);
        HashNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                break;
            }
            prev = head;
            head = head.next;
        }

        if (head == null) {
            return;
        }

        countOfItems--;
        if (prev != null) {
            prev.next = head.next;
        } else {
            buckets.set(bucketIndex, head.next);
        }
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    private void resize() {
        List<HashNode<K, V>> temp = buckets;
        capacity *= 2;
        countOfItems = 0;
        buckets = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            buckets.add(null);
        }

        for (HashNode<K, V> headNode : temp) {
            while (headNode != null) {
                put(headNode.key, headNode.value);
                headNode = headNode.next;
            }
        }
    }
}
