package ru.job4j.cars.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.10.18
 */
public interface Entity {

    int getId();

    String getName();

    void setId(int id);

    void setName(String name);
}
