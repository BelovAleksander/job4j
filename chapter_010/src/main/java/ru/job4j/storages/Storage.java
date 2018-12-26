package ru.job4j.storages;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
public interface Storage {
    void add(final User user);
    boolean isEmpty();
}
