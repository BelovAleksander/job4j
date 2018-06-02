package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayAddition {
    /**
     * Метод складывает элементы двух массивов сохраняя
     * сортировку по возрастанию.
     * @param first сортированный целочисленный массив.
     * @param second сортированный целочисленный массив.
     * @return результат сложения двух массивов.
     */
    public final int[] summa(final int[] first, final int[] second) {
        int i = 0;
        int j = 0;
        int length = first.length + second.length;
        int[] result = new int[length];
        for (int index = 0; index < length; index++) {
            if (i == first.length) {
                result[index] = second[j];
                j++;
            } else if (j == second.length) {
                result[index] = first[i];
                i++;
            } else if (first[i] < second[j]) {
                result[index] = first[i];
                i++;
            } else if (second[j] < first[i]) {
                result[index] = second[j];
                j++;
            }
        }
        return result;
    }
}
