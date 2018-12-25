package ru.job4j.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplexBean {
    AlfaBean alfa;

    @Autowired
    public ComplexBean(AlfaBean alfa) {
        this.alfa = alfa;
        System.out.println("complex bean");
    }
}
