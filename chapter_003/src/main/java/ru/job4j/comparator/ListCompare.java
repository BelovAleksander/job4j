package ru.job4j.comparator;

import java.util.Comparator;
import java.util.List;

/**
 * Собственная реализация String.compareTo().
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 19.06.18
 */

public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int result = 0;
        int length = left.length() <= right.length() ? left.length() : right.length();
        char[] leftArray = left.toCharArray();
        char[] rightArray = right.toCharArray();
        for (int index = 0; index < leftArray.length; index++) {
            if (Character.compare(leftArray[index], rightArray[index]) != 0) {
                result = Character.compare(leftArray[index], rightArray[index]);
                break;
            }
        }
        if ((result == 0) && (left.length() != right.length())) {
            result = left.length() > right.length() ? 1 : -1;
        }
        return result;
    }
}
