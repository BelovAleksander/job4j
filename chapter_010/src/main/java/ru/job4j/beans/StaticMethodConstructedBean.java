package ru.job4j.beans;

public class StaticMethodConstructedBean {
    private StaticMethodConstructedBean() {
        System.out.println("by static method");
    }

    public static StaticMethodConstructedBean builder() {
        return new StaticMethodConstructedBean();
    }
}
