package ru.job4j.set;

import ru.job4j.list.DynamicLinkedList;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 26.06.18
 * Класс реализует простую вариацию множества на связанном списке,
 * метод add проверяет входящие значения на уникальность.
 */
public class SimpleLinkedSet<E> implements Iterable<E> {
    private DynamicLinkedList<E> array;

    public SimpleLinkedSet() {
        this.array = new DynamicLinkedList<>();
    }

    public int getSize() {
        return this.array.getSize();
    }

    /**
     * Метод проверяет входящее значение на уникальность относительно
     * значений в массиве, и при true добавляет в массив.
     *
     * @param value входящее значение
     */
    public void add(E value) {
        if (this.array.isEmpty()) {
            this.array.add(value);
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
        SimpleLinkedSet<?> simpleSet = (SimpleLinkedSet<?>) o;
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
