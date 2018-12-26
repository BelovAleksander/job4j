package ru.job4j.beans;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
public class BeanFactory {
    public CustomBean createBean() {
        System.out.println("by factory");
        return new CustomBean();
    }
}
