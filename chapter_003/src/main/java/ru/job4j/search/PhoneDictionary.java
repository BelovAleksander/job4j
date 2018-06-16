package ru.job4j.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс создает и заполняет поля, хранящие
 * контактную инфформацию.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.06.18
 */

public class PhoneDictionary {
    private final List<Person> persons = new ArrayList<Person>();

    public final void add(final Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     * @param key Ключ поиска.
     * @return Список подощедщих пользователей.
     */
    public final List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (Person person : this.persons) {
            if ((person.getName().contains(key))
                    || (person.getSurname().contains(key))
                    || (person.getAddress().contains(key))
                    || (person.getPhone().contains(key))) {
                result.add(person);
            }
        }
        return result;
    }
}
