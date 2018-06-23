package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для возвращения четных чисел из массивов int[].
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 4.08.18
 */
public class EventIterator implements Iterator {
    /**
     * массив int[]
     */
    private int[] array;
    /**
     * указатель
     */
    private int index;

    public EventIterator(int[] array) {
        this.array = array;
    }

    /**
     * Метод возвращает индекс ближайшего четного числа
     * или -1, если такового не обнаружится.
     * @return index или -1
     */
    public int indexOfEvent() {
        int result = -1;
        for (int i = this.index; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает true, если впереди имеются четные числа.
     * @return true или false
     */
    public boolean hasNext() {
        return indexOfEvent() != -1;
    }

    /**
     * Метож возвращает следующее четное число и смещает указатель
     * или выбрасывает исключение.
     * @return следующее четное число из массива
     */
    public Object next() {
        if (index >= array.length || indexOfEvent() == -1) {
            throw new NoSuchElementException("No such element!");
        }
        int result = array[indexOfEvent()];
        index = indexOfEvent() + 1;
        return result;
    }
}
