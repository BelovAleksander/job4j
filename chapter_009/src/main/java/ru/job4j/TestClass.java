package ru.job4j;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class TestClass {
    private static SessionFactory factory;
    private static TestClass INSTANCE = new TestClass();
    private static String CONFIG = "/resources/hibernate.cfg.xml";

    private TestClass() {

    }


    public static TestClass getInstance() {
        openFactory();
        return INSTANCE;
    }

    public static TestClass getInstance(final String config) {
        CONFIG = config;
        openFactory();
        return INSTANCE;
    }

    public static void openFactory() {
        factory = new Configuration().configure(CONFIG).buildSessionFactory();
    }
    public static void main(String[] args) {
        System.out.println(System.getProperties());
        System.out.println(System.getProperty("user.dir"));
    }
}
