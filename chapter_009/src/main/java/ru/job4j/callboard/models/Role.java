package ru.job4j.callboard.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.10.2018
 */
public class Role {
    private int id;
    private String name;

    public Role() {

    }

    public Role(final int id) {
        this.id = id;
    }

    public Role(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
