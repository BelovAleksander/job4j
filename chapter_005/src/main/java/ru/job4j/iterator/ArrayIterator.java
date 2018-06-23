package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для масссивов.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 4.08.18
 */
public class ArrayIterator implements Iterator {
    /**
     * массив
     */
    public int[][] array;
    /**
     * индекс
     */
    public int index = 0;
    /**
     * индекс во вложенном массиве
     */
    public int position = 0;

    public ArrayIterator(final int[][] array) {
        this.array = array;
    }

    /**
     * Метод указывает на то, что обход массива еще не завершен.
     * @return true или false
     */
    @Override
    public boolean hasNext() {
        return (this.index < this.array.length);
    }

    /**
     * Метод возвращает значение и переводит каретку.
     * @return array[index][position]
     */
    @Override
    public Object next() {
        if (this.index >= this.array.length) {
            throw new NoSuchElementException("No such element");
        }
        int result = array[index][position++];
        if (this.position == array[index].length) {
            this.position = 0;
            index++;
        }
        return result;
    }
}
