package ru.job4j.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 */
public class Address extends Entity {
    private static final String TABLE = "addresses";

    public Address(int id, String name) {
        super(id, name);
    }

    public Address() {
    }

    public Address(String name) {
        super(name);
    }

    public String getTable() {
        return TABLE;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public int getId() {
        return super.getId();
    }

    public void setId(int id) {
        super.setId(id);
    }
}
