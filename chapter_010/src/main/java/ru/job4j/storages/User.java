package ru.job4j.storages;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
public class User {
    private String name;

    public User(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
