package ru.job4j.list;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 19.06.18
 */
public class User implements Comparable<User> {
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
