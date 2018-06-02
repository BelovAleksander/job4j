package ru.job4j.converter;

/**
 * Конвертер валюты.
 */
public class Converter {
    /**
     * курс евро.
     */
    static final int EURO_COURSE = 70;
    /**
     * курс доллара.
     */
    static final int DOLLAR_COURSE = 60;

    /**
     * Конвертируем рубли в евро.
     *
     * @param value рубли.
     * @return Евро.
     */
    public final int rubleToEuro(final int value) {
        return value / EURO_COURSE;
    }

    /**
     * Конвертируем рубли в доллары.
     *
     * @param value рубли.
     * @return Доллары.
     */
    public final int rubleToDollar(final int value) {
        return value / DOLLAR_COURSE;
    }

    /**
     * Конвертируем евро в рубли.
     *
     * @param value евро.
     * @return Рубли.
     */
    public final int euroToRuble(final int value) {
        return value * EURO_COURSE;
    }

    /**
     * Конвертируем доллары в рубли.
     *
     * @param value доллары.
     * @return Рубли.
     */
    public final int dollarToRuble(final int value) {
        return value * DOLLAR_COURSE;
    }
}
