package ru.job4j.autowired;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.beans.CustomBean;
import ru.job4j.beans.DefaultConstructedBean;
import ru.job4j.beans.StaticMethodConstructedBean;

import static org.junit.Assert.*;

public class ComplexBeanTest {

    @Test
    public void createDifferentBeans() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        DefaultConstructedBean first = context.getBean(DefaultConstructedBean.class);
        StaticMethodConstructedBean second = context.getBean(StaticMethodConstructedBean.class);
        CustomBean third = context.getBean(CustomBean.class);
        ComplexBean fourth = context.getBean(ComplexBean.class);

        assertNotNull(first);
        assertNotNull(second);
        assertNotNull(third);
        assertNotNull(fourth);
    }

}