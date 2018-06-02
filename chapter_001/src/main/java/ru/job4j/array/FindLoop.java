package ru.job4j.array;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class FindLoop {
    /**
     * Ищет элемент в массиве путем перебора.
     *
     * @param data массив.
     * @param el   искомый элемент.
     * @return индекс найденного элемента.
     */
    public final int indexOf(final int[] data, final int el) {
        int rst = -1;
        for (int index = 0; index < data.length; index++) {
            if (data[index] == el) {
                rst = index;
                break;
            }
        }
        return rst;
    }
}
