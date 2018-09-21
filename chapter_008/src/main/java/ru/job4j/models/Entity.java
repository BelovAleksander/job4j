package ru.job4j.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 08.09.18
 */
public class Entity {
    private int id;
    private String name;

    public Entity() {

    }

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Entity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
}
