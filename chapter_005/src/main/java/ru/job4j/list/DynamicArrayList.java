package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс создает динамический список на базе массива.
 * Реализует Iterable.
 *
 * @param <E> задаваемый входящий тип
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.06.18
 */
@ThreadSafe
public class DynamicArrayList<E> implements Iterable<E> {
    @GuardedBy("this")
    private Object[] container;
    @GuardedBy("this")
    private int index = 0;
    private int size;
    /**
     * счетчик изменений
     */
    private int modCount = 0;
    /**
     * счетчик для итератора
     */


    public DynamicArrayList(int size) {
        this.size = size;
        this.container = new Object[size];
    }

    /**
     * Добавление элемента.
     *
     * @param value новый элемент
     */
    public synchronized void add(E value) {
        this.container[this.index++] = value;
        if (this.size == this.index) {
            this.container = grow();
        }
        this.modCount++;
    }

    /**
     * Возвращение элемента.
     *
     * @param index индекс
     * @return элемент
     */
    public synchronized E get(int index) {
        return (E) this.container[index];
    }

    /**
     * Увеличение size массива.
     *
     * @return увеличенный массив
     */
    public synchronized Object[] grow() {
        this.modCount++;
        size *= 2;
        return Arrays.copyOf(this.container, this.size * 2);
    }

    /**
     * Возвращает размер массива.
     *
     * @return размер
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Итератор реализует поведение fail-fast.
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        int modCountCopy = this.modCount;

        return new Iterator<E>() {
            int itCount = 0;
            @Override
            public boolean hasNext() {
                return itCount < size - 1 && container[itCount] != null;
            }

            @Override
            public E next() {
                if (modCountCopy != modCount) {
                    throw new ConcurrentModificationException("Concurrent modification!");
                }
                if (itCount == size) {
                    throw new NoSuchElementException("No such element!");
                }
                return (E) container[itCount++];
            }
        };
    }
}
