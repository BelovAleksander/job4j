package ru.job4j.cars.models;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.10.18
 */
@Entity
@Table(name = "carcases")
public class CarcaseAnts implements ru.job4j.cars.models.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", unique = true)
    private String name;

    public CarcaseAnts() {

    }

    public CarcaseAnts(String name) {
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
