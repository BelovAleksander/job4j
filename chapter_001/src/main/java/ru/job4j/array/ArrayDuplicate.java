package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {
    /**
     * Метод удаляет дубликаты в массиве.
     * @param array массив строк.
     * @return массив без дубликатов.
     */
    public final String[] remove(final String[] array) {
        String variable;
        // flag - требуется для подсчета отброшенных элементов массива
        int flag = 0;
        // end - конечный индекс массива
        int end = array.length - 1;
        for (int i = 0; i < array.length - flag; i++) {
            System.out.println("i: " + array[i]);
            for (int j = i + 1; j < array.length - flag; j++) {
                // подскажите, как изящнее ибавиться от вложенных if? Или норм?
                if (array[i].equals(array[j])) {
                    if (array[i].equals(array[end - flag])) {
                        flag += 1;
                    }
                    variable = array[end - flag];
                    array[end - flag] = array[j];
                    array[j] = variable;
                    flag += 1;
                }
            }
        }
        return Arrays.copyOf(array, array.length - flag);
    }
}
