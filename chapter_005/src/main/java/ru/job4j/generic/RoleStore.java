package ru.job4j.generic;

/**
 * Класс-хранилище для объектов типа Role.
 * @param  <Role> роль
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */

public class RoleStore<Role> extends AbstractStore {
    public RoleStore(int size) {
        super(size);
    }
}
