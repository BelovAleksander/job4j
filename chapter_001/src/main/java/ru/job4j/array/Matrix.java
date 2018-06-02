package ru.job4j.array;

/**
 * @author whiterabbit.nsk
 * @version $Id$
 * @since 0.1
 */
public class Matrix {
    /**
     * Создает двумерный массив заданной величины.
     * @param size размер массива.
     * @return массив.
     */
    public final int[][] multiple(final int size) {
        int[][] data = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = (i + 1) * (j + 1);
            }
        }
        return data;
    }
}
