package ru.job4j.callboard.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.10.2018
 */
public class Model {
    private int id;
    private String name;
    @JsonIgnore
    private List<Advert> adverts;
    private Brand brand;

    public Model() {

    }

    public Model(final int id) {
        this.id = id;
    }

    public Model(final String name, final Brand brand, final List<Advert> adverts) {
        this.name = name;
        this.brand = brand;
        this.adverts = adverts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public void addChild(final Advert advert) {
        this.adverts.add(advert);
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @JsonIgnore
    public int getAdvertsSize() {
        return this.adverts.size();
    }

}
