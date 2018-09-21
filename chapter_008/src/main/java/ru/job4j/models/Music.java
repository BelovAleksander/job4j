package ru.job4j.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 */
public class Music extends Entity {
    private static final String TABLE = "music";

    public Music(int id, String name) {
        super(id, name);
    }

    public Music() {

    }
    public Music(String name) {
        super(name);
    }

    public String getTable() {
        return TABLE;
    }

    public int getId() {
        return super.getId();
    }

    public void setId(int id) {
        super.setId(id);
    }

    public String getName() {
        return super.getName();
    }
    public void setName(String name) {
        super.setName(name);
    }
}
