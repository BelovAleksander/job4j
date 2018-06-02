package ru.job4j.calculator;

/**
 * Class Calculator.
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version 0.1
 * @since 24.05.2018
 */

class Calculator {
    /**
     * Результат.
     */
    private double result;

    /**
     * Сложение.
     *
     * @param first  первый аргумент.
     * @param second второй аргумент.
     */
    public final void add(final double first, final double second) {
        this.result = first + second;
    }

    /**
     * Вычитание.
     *
     * @param first  первый аргумент.
     * @param second второй аргумент.
     */
    public final void subtract(final double first, final double second) {
        this.result = first - second;
    }

    /**
     * Умножение.
     *
     * @param first  первый аргумент.
     * @param second второй аргумент.
     */
    public final void multiple(final double first, final double second) {
        this.result = first * second;
    }

    /**
     * Деление.
     *
     * @param first  первый аргумент.
     * @param second второй аргумент.
     */
    public final void div(final double first, final double second) {
        this.result = first / second;
    }

    /**
     * Возвращает результат.
     *
     * @return результат.
     */
    public final double getResult() {
        return this.result;
    }
}
