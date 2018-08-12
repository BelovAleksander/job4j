package ru.job4j.set;

import ru.job4j.list.DynamicArrayList;

import java.util.Iterator;
import java.util.Objects;

/**
 * @param <E> любой тип
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 26.06.18
 * Класс реализует простую вариацию множества, метод add
 * проверяет входящие значения на уникальность.
 */
public class SimpleSet<E> implements Iterable<E> {
    private DynamicArrayList<E> array;
    private int counter;

    public SimpleSet() {
        this.array = new DynamicArrayList<>(10);
    }

    public boolean isEmpty() {
        return this.counter == 0;
    }

    public int size() {
        return this.counter;
    }

    /**
     * Метод проверяет входящее значение на уникальность относительно
     * значений в массиве, и при true добавляет в массив.
     *
     * @param value входящее значение
     */
    public void add(E value) {
        if (this.isEmpty()) {
            this.array.add(value);
            this.counter++;
        } else {
            boolean isDuplicate = false;
            for (E el : this.array) {
                if (el != null && el.equals(value)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                this.array.add(value);
                this.counter++;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleSet<?> simpleSet = (SimpleSet<?>) o;
        return Objects.equals(this.array, simpleSet.array);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.array);
    }

    @Override
    public Iterator<E> iterator() {
        return this.array.iterator();
    }
}
