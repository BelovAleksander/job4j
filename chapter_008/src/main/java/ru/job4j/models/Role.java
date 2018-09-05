package ru.job4j.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 */
public class Role {
    private final int id;
    private String name;

    public Role(int id, String role) {
        this.id = id;
        this.name = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return name;
    }

    public void setRole(String role) {
        this.name = role;
    }
}
