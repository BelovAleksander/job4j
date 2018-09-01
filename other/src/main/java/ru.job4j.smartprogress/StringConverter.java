package ru.job4j.smartprogress;

import java.util.*;

public class StringConverter {
    /**
     * Ровняем по регистру, разбиваем строку на слова, меняем регистр первой буквы каждого слова.
     * @param string
     * @return result
     */
    public String convertEasy(final String string) {
        String[] array = string.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            sb.append(str).append(" ");
        }
        return sb.toString();

    }

    /**
     * Удаляем последнюю точку (последний символ конца строки), опускаем регистр строки,
     * разделяем строку на предложения, меняем порядок букв в каждом предложении на обратный,
     * поднимаем регистр первой буквы каждого предложения, склеиваем предложения с добавлением точек.
     * @param string
     * @return result
     */
    public String convertMedium(String string) {
        String[] array = string.substring(0, string.length() - 1).toLowerCase().split("\\. ");
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            String reflection = reverse(str);
            sb.append(reflection.substring(0, 1).toUpperCase())
                    .append(reflection.substring(1))
                    .append(". ");
        }
        return sb.toString();
    }

    public void out() {
        Set<String> set = new HashSet<>();
    }

    /**
     * Зеркалит строку.
     * @param string
     * @return отражение строки.
     */
    private String reverse(String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = string.length(); i != 0; i--) {
            sb.append(string.charAt(i - 1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StringConverter converter = new StringConverter();
        String easy = "ИВАнов иВан иваНОВИЧ.";
        String medium = "мАма мЫЛА рАму. маШа игрАла в мЯч.";
        System.out.println(converter.convertEasy(easy));
        System.out.println(converter.convertMedium(medium));
    }
}