package ru.job4j.beans;

import ru.job4j.autowired.AlfaBean;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
public class CustomBean implements AlfaBean {
    @Override
    public void print() {
        System.out.println("some actions...");
    }
}
