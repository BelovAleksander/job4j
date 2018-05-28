package ru.job4j.loop;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Factorial {
    /**
     * Вычисляет факториал числа.
     * @param n натуральное целое число.
     * @return факториал n.
     */

    public int calc(int n) {
        int rsl = 1;
        for (int i = 1; i <= n; i++){
                rsl *= i;
        }
        return rsl;
    }
}
