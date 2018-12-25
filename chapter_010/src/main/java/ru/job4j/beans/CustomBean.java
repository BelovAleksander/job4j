package ru.job4j.beans;

import ru.job4j.autowired.AlfaBean;

public class CustomBean implements AlfaBean {
    @Override
    public void print() {
        System.out.println("some actions...");
    }
}
