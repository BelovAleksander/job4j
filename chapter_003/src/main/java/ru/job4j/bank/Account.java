package ru.job4j.bank;

/**
 * Класс описывает банковский счет.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 20.06.18
 */
public class Account {
    /**
     * баланс
     */
    private double value;
    /**
     * реквизиты счета
     */
    private final String requisites;

    /**
     * Конструктор.
     * @param value баланс
     * @param requisites реквизиты счета
     */
    public Account(final double value, final String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    /**
     * геттер для реквизитов.
     * @return реквизиты счета
     */
    public String getRequisites() {
        return this.requisites;
    }

    /**
     * геттер для баланса
     * @return баланс
     */
    public double getValue() {
        return this.value;
    }

    /**
     * изменение баланса
     * @param value сумма изменений
     */
    public void changeValue(double value) {
        this.value += value;
    }
}
