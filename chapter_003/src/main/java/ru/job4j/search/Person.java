package ru.job4j.search;

/**
 * Класс создает и заполняет поля, хранящие
 * контактную инфформацию.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.06.18
 */

public class Person {
    /**
     * имя.
     */
    private final String name;
    /**
     * фамилия.
     */
    private final String surname;
    /**
     * телефон.
     */
    private final String phone;
    /**
     * адрес.
     */
    private final String address;

    public Person(final String name, final String surname, final String phone, final String address) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public final String getName() {
        return name;
    }

    public final String getSurname() {
        return surname;
    }

    public final String getPhone() {
        return phone;
    }

    public final String getAddress() {
        return address;
    }
}
