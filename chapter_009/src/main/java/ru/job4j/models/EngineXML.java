package ru.job4j.models;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.10.18
 */
public class EngineXML implements Entity {
    private int id;
    private String name;

    public EngineXML() {

    }

    public EngineXML(final String name) {
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
