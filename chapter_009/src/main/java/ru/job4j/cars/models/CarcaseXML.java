package ru.job4j.cars.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.10.18
 */
public class CarcaseXML implements Entity {
    private int id;
    private String name;

    public CarcaseXML() {

    }

    public CarcaseXML(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
