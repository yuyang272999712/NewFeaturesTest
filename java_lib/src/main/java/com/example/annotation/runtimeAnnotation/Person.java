package com.example.annotation.runtimeAnnotation;

/**
 * 使用运行时注解
 */
@ClassInfo(author = "于洋",date = "2016-08-14", version = 1.1f)
public class Person {
    private int    id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean equals(Person person) {
        return person.id == id;
    }

    public int hashCode() {
        return id;
    }

    /*public static void main(String[] args) {

        Set<Person> set = new HashSet<Person>();
        for (int i = 0; i < 10; i++) {
            set.add(new Person(i, "Jim"));
        }
        System.out.println(set.size());
    }*/
}
