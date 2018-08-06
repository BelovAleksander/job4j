package ru.job4j.generic;

/**
 * Класс-хранилище для объектов типа Role.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */

public class RoleStore extends AbstractStore<Role> {
    public RoleStore(int size) {
        super(size);
    }
}
