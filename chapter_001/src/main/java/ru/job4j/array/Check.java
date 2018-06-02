package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Check {
    /**
     * Проверяет массив на идентичность значений.
     * @param data входящий массив.
     * @return true, если значения идентичны.
     */
    public final boolean mono(final boolean[] data) {
        boolean result = true;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != data[0]) {
                result = false;
                break;
            }
        }
        return result;
    }
}
