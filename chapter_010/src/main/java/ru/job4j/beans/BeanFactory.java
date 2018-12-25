package ru.job4j.beans;

public class BeanFactory {
    public CustomBean createBean() {
        System.out.println("by factory");
        return new CustomBean();
    }
}
