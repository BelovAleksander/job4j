package ru.job4j.list;

import java.util.Random;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 19.06.18
 */
public class User {
    private final String name;
    private final String city;
    private final int id;

    public User(String name, String city) {
        this.name = name;
        this.city = city;
        this.id = setId();
        System.out.println(this.id);
    }

    private int setId() {
        Random rn = new Random(1000);
        return rn.nextInt();
    }

    public int getId() {
        return this.id;
    }
}
