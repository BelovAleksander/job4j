package ru.job4j.set;

/**
 * Класс реализует упрощенный вариант множества
 * реализованного на базе хеш-таблицы.
 *
 * @param <E> Любой тип
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.06.18
 */

public class SimpleHashSet<E> {
    private SimpleHash<E> hash;

    public SimpleHashSet(int size) {
        this.hash = new SimpleHash<>(size);
    }

    public boolean add(E value) {
        return hash.add(value);
    }

    public boolean contains(E value) {
        return hash.find(value);
    }

    public boolean remove(E value) {
        return hash.remove(value);
    }

    public int getSize() {
        return this.hash.getSize();
    }
}
