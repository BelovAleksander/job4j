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

    private int[] array = new int[50];

    /**
     * Метод вычисляет количество и тип
     * возвращаемых монет.
     * @param value внесенная сумма
     * @param price цена продукта
     * @return массив с монетами
     */
    public final int[] changes(final int value, int price) {
        int flag = 0;
        for (int index = 0; price != value; index++) {
            if (this.array.length - index == 1) {
                this.array = expandedArray();
            }
            if (value - price >= COIN10) {
                this.array[index] = COIN10;
                price += COIN10;
            } else if (value - price >= COIN5) {
                this.array[index] = COIN5;
                price += COIN5;
            } else if (value - price >= COIN2) {
                this.array[index] = COIN2;
                price += COIN2;
            } else {
                this.array[index] = COIN1;
                price += COIN1;
            }
            flag++;
        }
        return Arrays.copyOf(this.array, flag);
    }

    public int[] expandedArray() {
        int[] newArray = new int[this.array.length * 2];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        return newArray;
    }
}
