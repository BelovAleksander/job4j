package ru.job4j.tests;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Проверяет входящую строку на разницу
 * числа открывающих и закрывающих скобок.
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 21.06.18
 */

public class Parsing {
    public boolean isValide(String string) {
        boolean result = true;
        if ((string.length() == 1 || string.startsWith("}"))
                || (string.startsWith("]")) || (string.startsWith(")"))) {
            result = false;
        } else {
            char bracketStart = '\0';
            char bracketEnd = '\0';
            int flagStart = -1;
            int flagEnd = -1;
            int nesting = 0; // вложенность
            char[] array = string.toCharArray();
            int openBrackets = 0;
            int closeBrackets = 0;
            for (int index = 0; index < array.length; index++) {
                if (array[index] == bracketStart) {
                    nesting++;
                } else if ((nesting == 0 && array[index] == 123)
                        || (nesting == 0 && array[index] == 91)
                        || (nesting == 0 && array[index] == 40)) {
                    bracketStart = array[index];
                    bracketEnd = bracketStart == 40 ? ')' : (char) (bracketStart + 2);
                    flagStart = index;
                    nesting++;
                } else if (array[index] == bracketEnd) {
                    if (nesting == 1) {
                        flagEnd = index;
                        result = isValide(
                                String.copyValueOf(
                                        array, flagStart + 1, flagEnd - flagStart - 1));
                        if (!result) {
                            break;
                        }
                        bracketStart = '\0';
                        bracketEnd = '\0';
                    }
                    nesting--;
                }
            }
            for (char bracket : array) {
                if (bracket == 123 || bracket == 40 || bracket == 91) {
                    openBrackets++;
                } else if (bracket == 125 || bracket == 41 || bracket == 93) {
                    closeBrackets++;
                } else {
                    result = false; // Если не допустимо ничего кроме скобок.
                }
            }
            if (openBrackets != closeBrackets) {
                result = false;
            }
        }
        return result;
    }
}
