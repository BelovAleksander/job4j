package ru.job4j.generic;

/**
 * Класс-хранилище для объектов типа User.
 * @param <User> пользователь
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */

public class UserStore<User> extends AbstractStore {
    public UserStore(int size) {
        super(size);
    }
}
