package ru.job4j.callboard.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.10.2018
 */
public class Brand {
    private int id;
    private String name;
    @JsonIgnore
    private List<Model> models;

    public Brand() {

    }

    public Brand(final int id) {
        this.id = id;
    }

    public Brand(final String name, final List<Model> models) {
        this.name = name;
        this.models = models;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setModels(final List<Model> models) {
        this.models = models;
    }

    public void addChild(final Model model) {
        this.models.add(model);
    }

    @JsonIgnore
    public int getModelsSize() {
        return models.size();
    }

    @Override
    public String toString() {
        return "Brand{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
