package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Метод класса конвертирует двумерный
 * массив в List.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 */
public class ConvertMatrix2List {
    public final List<Integer> toList(final int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int[] row : array) {
            for (int cell : row) {
                list.add(cell);
            }
        }
        return list;
    }
}
