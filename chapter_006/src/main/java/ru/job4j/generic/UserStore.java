package ru.job4j.generic;

/**
 * Класс-хранилище для объектов типа User.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */

public class UserStore extends AbstractStore<User> {
    public UserStore(int size) {
        super(size);
    }
}
