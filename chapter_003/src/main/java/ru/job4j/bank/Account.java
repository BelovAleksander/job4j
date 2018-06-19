package ru.job4j.bank;

public class Account {
    private double value;
    private final String requisites;

    public Account(final double value, final String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public String getRequisites() {
        return this.requisites;
    }

    public double getValue() {
        return this.value;
    }

    public void changeValue(double value) {
        this.value += value;
    }
}
