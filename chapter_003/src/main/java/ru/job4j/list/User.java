package ru.job4j.list;

import java.util.Random;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 19.06.18
 */
public class User implements Comparable<User> {
    private final String name;
//    private final String city;
    private final int age;
//    private final int id;

    public User(String name, int age) {
        this.name = name;
//        this.city = city;
//        this.id = setId();
        this.age = age;
    }

    private int setId() {
        Random rn = new Random(1000);
        return rn.nextInt();
    }

//    public int getId() {
//        return this.id;
//    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int compareTo(User o1) {
        return Integer.compare(this.getAge(), o1.getAge());
    }
}
