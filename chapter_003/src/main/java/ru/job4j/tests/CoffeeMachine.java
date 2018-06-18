package ru.job4j.tests;

import java.util.Arrays;

/**
 * Класс содержит метод, вычисляющий тип и количество
 * монет, возвращаемых пользователю как разницу между
 * value и price.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 18.06.18
 */

public class CoffeeMachine {
    private final static int COIN1 = 1;
    private final static int COIN2 = 2;
    private final static int COIN5 = 5;
    private final static int COIN10 = 10;
    /**
     * Максимальное количество возвращаемых монет.
     */
    private final static int MAX_COINS_IN_CHANGE = 50;

    /**
     * Метод вычисляет количество и тип
     * возвращаемых монет.
     * @param value внесенная сумма
     * @param price цена продукта
     * @return массив с монетами
     */
    public final int[] changes(final int value, int price) {
        int[] array = new int[MAX_COINS_IN_CHANGE];
        int flag = 0;
        for (int index = 0; price != value; index++) {
            if (value - price >= COIN10) {
                array[index] = COIN10;
                price += COIN10;
            } else if (value - price >= COIN5) {
                array[index] = COIN5;
                price += COIN5;
            } else if (value - price >= COIN2) {
                array[index] = COIN2;
                price += COIN2;
            } else {
                array[index] = COIN1;
                price += COIN1;
            }
            flag++;
        }
        return Arrays.copyOf(array, flag);
    }
}
