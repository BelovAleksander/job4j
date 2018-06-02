package ru.job4j.loop;

import java.util.function.BiPredicate;

/**
 * @author Alexsnder Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * Логика вырисовки правой части пирамиды.
     *
     * @param height высота.
     * @return правый треугольник.
     */
    public final String rightTrl(final int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= column
        );
    }

    /**
     * Логика вырисовки левой части пирамиды.
     *
     * @param height высота.
     * @return левый треугольник.
     */

    public final String leftTrl(final int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= height - column - 1
        );
    }


    /**
     * Пирамида.
     *
     * @param height высота пирамиды.
     * @return пирамида в строковом представлении.
     */

    public final String pyramid(final int height) {
        return this.loopBy(
                height,
                2 * height - 1,
                (row, column) -> row >= height - column - 1
                        && row + height - 1 >= column);
    }

    /**
     * Рисует пирамиду.
     *
     * @param height  высота.
     * @param weight  ширина.
     * @param predict класс библиотеки.
     * @return рисунок.
     */
    private String loopBy(final int height, final int weight, final
    BiPredicate<Integer, Integer> predict) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (predict.test(row, column)) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
}
