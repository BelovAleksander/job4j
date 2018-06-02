package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk)
 * @version $Id$
 * @since 0.1
 */
public class Square {
    /**
     * Метод создает последовательность чисел, возведенных в квалрат.
     * @param bound количество чисел в массиве.
     * @return массив.
     */
    public final int[] calculate(final int bound) {
        int[] rst = new int[bound];
        int i = 0;
        for (int num = 1; num <= bound; num++) {
            rst[i] = (int) Math.pow(num, 2);
            i++;
        }
        return rst;
    }
}
