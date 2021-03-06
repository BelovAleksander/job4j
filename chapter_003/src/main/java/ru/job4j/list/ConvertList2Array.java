package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс имеет метод, конвертирующий List  в двумерный массив.
 * @author Alexander Belov (whiterabiit.nsk@gmail.com)
 * @since 17.06.18
 */

public class ConvertList2Array {
    /**
     * toArray конвертирует List в двумерный массив.
     * @param list List
     * @param rows Количество ячеек в столбцах двумерного масива
     * @return
     */
    public final int[][] toArray(final List<Integer> list, final int rows) {
        int cells = (int) Math.ceil((double) list.size() / rows);
        int[][] array = new int[rows][cells];
        int row = 0;
        int cell = 0;
        for (int el : list) {
            if (cell == cells) {
                cell = 0;
                row++;
            }
            array[row][cell] = el;
            cell++;
        }
        return array;
    }
    public final List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] row : list) {
            for (int cell : row) {
                result.add(cell);
            }
        }
        return result;
    }
}
