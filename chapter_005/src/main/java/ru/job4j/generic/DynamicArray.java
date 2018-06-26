package ru.job4j.generic;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Универсальный класс-обертка для массива.
 * @param <T> Любой класс
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */

public class DynamicArray<T> implements Iterable<T> {
    /**
     * массив для заполнения
     */
    private T[] array;
    /**
     * указатель массива
     */
    private int index = 0;
    /**
     * задаваемый размер массива
     */
    private int size;

    public DynamicArray(int size) {
        this.size = size;
        this.array = (T[]) new Object[size];
    }

    /**
     * Метод добавляет элемент в массив.
     * @param element добавляемый элемент
     */
    public void add(T element) {
        if (this.index == this.size) {
            throw new StackOverflowError("Array is full!");
        }
        this.array[this.index] = element;
        this.index++;
    }

    /**
     * Метод добавляет элемент по индексу.
     * @param index индекс
     * @param element элемент
     */
    public void set(int index, T element) {
        this.array[index] = element;
    }

    /**
     * Метод удаляет элемент по индексу.
     * @param index индекс
     */
    public void delete(int index) {
        this.array[index] = null;
    }

    /**
     * Метод возврашает элемент по индексу.
     * @param index индекс
     * @return элемент
     */
    public T get(int index) {
        return this.array[index];
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(this.array).iterator();
    }
}
