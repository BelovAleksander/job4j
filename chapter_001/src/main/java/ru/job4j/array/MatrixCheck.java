package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MatrixCheck {
    /**
     * Проверяет идентичность значений массива.
     * @param data массив.
     * @return результат проверки.
     */
    public final boolean mono(final boolean[][] data) {
        boolean result = true;
        for (int i = 0; i < data.length; i++) {
            if ((data[i][i] != data[0][0])
                    || (data[i][data.length - i - 1] != data[0][0])) {
                result = false;
                break;
            }
        }
        return result;
    }
}
