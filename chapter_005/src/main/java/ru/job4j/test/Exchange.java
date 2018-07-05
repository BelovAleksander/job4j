package ru.job4j.test;

import java.util.ArrayList;

public class Exchange {
    private ArrayList<Glass> issuers = new ArrayList<>();

    public void addIssuer(String name) {
        issuers.add(new Glass(name));
    }

    public boolean addItem(String book, boolean add, boolean ask,
                        double price, int volume) {
        boolean result = false;
        for (Glass glass : issuers) {
            if (glass.getName().equals(book)) {
                glass.process(new Item(book, add, ask, price, volume));
                result = true;
            }
        }
        return result;
    }

    public Glass getIssuer(int index) {
        return issuers.get(index);
    }
}
