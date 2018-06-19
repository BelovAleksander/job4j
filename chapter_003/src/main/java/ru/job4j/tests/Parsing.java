package ru.job4j.tests;

/**
 * Проверяет входящую строку на разницу
 * числа открывающих и закрывающих скобок.
 * Пока не придумал как отследить такие
 * неверные входящие значения:
 * "{([)]}"
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 18.06.18
 */

public class Parsing {
    public boolean isValide(String string) {
        boolean result = true;
        if ((string.startsWith("}")) || (string.startsWith("]")) || (string.startsWith(")"))) {
            result = false;
        } else {
            char[] array = string.toCharArray();
            StringBuilder openBracket = new StringBuilder();
            StringBuilder closeBracket = new StringBuilder();
            StringBuilder openFigure = new StringBuilder();
            StringBuilder closeFigure = new StringBuilder();
            StringBuilder openSquare = new StringBuilder();
            StringBuilder closeSquare = new StringBuilder();
            for (char bracket : array) {
                if (bracket == 123) {
                    openFigure.append(bracket);
                } else if (bracket == 125) {
                    closeFigure.append(bracket);
                } else if (bracket == 40) {
                    openBracket.append(bracket);
                } else if (bracket == 41) {
                    closeBracket.append(bracket);
                } else if (bracket == 91) {
                    openSquare.append(bracket);
                } else if (bracket == 93) {
                    closeSquare.append(bracket);
                } else {
                    result = false; // Если не допустимо ничего кроме скобок.
                }
            }
            if ((openBracket.length() != closeBracket.length())
                    || (openFigure.length() != closeFigure.length())
                    || (openSquare.length() != closeSquare.length())) {
                result = false;
            }
        }
        return result;
    }
}
