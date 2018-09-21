package ru.job4j.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 */
public class Role extends Entity {
    private static final String TABLE = "roles";

    public Role(int id, String name) {
        super(id, name);
    }

    public Role() {

    }
    public Role(String name) {
        super(name);
    }

    public String getTable() {
        return TABLE;
    }

    public String getName() {
        return super.getName();
    }

    public static String getTABLE() {
        return TABLE;
    }

    public void setName(String name) {
        super.setName(name);

    }

    public void setId(int id) {
        super.setId(id);
    }

    public int getId() {
        return super.getId();
    }
}
