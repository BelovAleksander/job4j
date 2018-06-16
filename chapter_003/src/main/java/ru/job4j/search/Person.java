package ru.job4j.search;

/**
 * Класс создает и заполняет поля, хранящие
 * контактную инфформацию.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.06.18
 */

public class Person {
    private String name;
    private String surname;
    private String phone;
    private String address;

    public Person(String name, String surname, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
