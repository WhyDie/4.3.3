package ua.whydie;

public class Main {
    public static void main(String[] args) {
        SeparateChaining<String, Integer> map = new SeparateChaining<>();

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        System.out.println("Size: " + map.size());
        System.out.println("Get 'one': " + map.get("one"));
        System.out.println("Get 'two': " + map.get("two"));

        map.delete("two");
        System.out.println("Get 'two' after deletion: " + map.get("two"));

        System.out.println("Contains 'three': " + map.contains("three"));
        System.out.println("Contains 'two': " + map.contains("two"));
    }
}
