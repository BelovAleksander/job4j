package ru.job4j.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
@Component
public class ComplexBean {
    AlfaBean alfa;

    @Autowired
    public ComplexBean(AlfaBean alfa) {
        this.alfa = alfa;
        System.out.println("complex bean");
    }
}
