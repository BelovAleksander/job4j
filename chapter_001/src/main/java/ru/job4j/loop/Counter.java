package ru.job4j.loop;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Counter {
    /**
     *
     * @param start начало диапазона.
     * @param finish конец диапазона.
     * @return сумма четных чисел заданного диапазона.
     */
    public final int add(final int start, final int finish) {
        int rsl = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                rsl += i;
            }
        }
        return rsl;
    }
}
