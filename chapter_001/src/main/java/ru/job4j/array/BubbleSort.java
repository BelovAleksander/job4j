package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class BubbleSort {
    /**
     * Метод сортировки пузыриком.
     * @param array входящий массив.
     * @return отсортированный масив.
     */
    public int[] sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int variable;
            int flag = 0;
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    variable = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = variable;
                    flag = 1;
                }
            }
            if (flag == 0) break;
            }
        return array;
    }
}
