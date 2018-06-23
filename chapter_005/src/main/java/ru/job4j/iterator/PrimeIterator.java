package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для возвращения простых чисел из массивов int[].
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 4.08.18
 */
public class PrimeIterator implements Iterator {
    /**
     * массив int[]
     */
    private int[] array;
    /**
     * указатель
     */
    private int index;

    public PrimeIterator(int[] array) {
        this.array = array;
    }

    /**
     * Метод возвращает индекс ближайшего простого числа
     * или -1, если такового не обнаружится.
     * @return index или -1
     */
    public int indexOfPrime() {
        int result = -1;
        for (int i = this.index; i < array.length; i++) {
            if (array[i] > 1 && isPrime(array[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Метод вычисляет является ли число простым.
     * @param number проверяемое значение
     * @return true, если простое
     */
    public boolean isPrime(int number) {
        boolean result = true;
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает true, если впереди имеются простые числа.
     * @return true или false
     */
    public boolean hasNext() {
        return indexOfPrime() != -1;
    }

    /**
     * Метож возвращает следующее простое число и смещает указатель
     * или выбрасывает исключение.
     * @return следующее простое число из массива
     */
    public Object next() {
        if (index >= array.length || indexOfPrime() == -1) {
            throw new NoSuchElementException("No such element!");
        }
        this.index = indexOfPrime();
        int result = array[index++];
        return result;
    }
}
