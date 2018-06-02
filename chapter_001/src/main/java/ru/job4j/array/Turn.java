package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Turn  {
    /**
     * Переворачивает массив.
     * @param array массив.
     * @return перевернутый массив.
     */
    public final int[] turn(final int[] array) {
        int variable;
        for (int start = 0, end = (array.length - 1);
             start < array.length / 2; start++, end--) {
            variable = array[start];
            array[start] = array[end];
            array[end] = variable;
        }
        return array;
    }
}
