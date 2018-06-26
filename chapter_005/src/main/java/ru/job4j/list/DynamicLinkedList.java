package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Класс создает динамический список на базе связанного списка.
 * Реализует Iterable.
 *
 * @param <E> задаваемый входящий тип
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.06.18
 */
public class DynamicLinkedList<E> implements Iterable<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;
    /**
     * счетчик изменений
     */
    private int modCount = 0;


    public DynamicLinkedList() {
        this.size = 0;
    }
    /**
     * Добавление элемента.
     *
     * @param data новый элемент
     */
    public void add(E data) {
        Node<E> newLink = new Node<>(data);
        if (this.first == null) {
            this.last = newLink;
        } else {
            this.first.previous = newLink;
        }
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
        this.modCount++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int getSize() {
        return this.size;
    }

    public E removeFirst() {
        E data = this.first.data;
        if (this.first.next == null) {
            this.last = null;
        }
        this.first = this.first.next;
        this.first.previous = null;
        this.size--;
        return data;
    }

    public E removeLast() {
        E data = this.last.data;
        if (this.last.previous == null) {
            this.first = null;
        }
        this.last = this.last.previous;
        this.last.next = null;
        this.size--;
        return data;
    }

    /**
     * Возвращение элемента.
     *
     * @param index индекс
     * @return элемент
     */
    public E get(int index) {
        Node<E> result = this.last;
        for (int i = 0; i < index; i++) {
            result = result.previous;
        }
        return result.data;
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
            /**
             * счетчик для итератора
             */
            private int itCount = 0;
            @Override
            public boolean hasNext() {
                return itCount < size - 1;
            }

            @Override
            public E next() {
                if (modCountCopy != modCount) {
                    throw new ConcurrentModificationException("Concurrent modification!");
                }
                return get(itCount++);
            }
        };
    }

    private static class Node<E> {
        E data;
        DynamicLinkedList.Node<E> next;
        DynamicLinkedList.Node<E> previous;

        Node(E data) {
            this.data = data;
        }
    }
}
