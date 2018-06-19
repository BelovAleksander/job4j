package ru.job4j.tracker;

import java.util.*;


/**
 * @author whiterabbit.nsk
 * @since 08.06.2018
 * Класс Tracker предоставляет набор методов
 * для обработки заявок.
 */
public class Tracker {
    /**
     * Массив для хранения заявок.
     */
    private final ArrayList<Item> items = new ArrayList<>();
    /**
     * Хранит рандомное значение.
     */
    private static final Random RN = new Random();

    /**
     * Результат работы метода указывает на то, что массив заявок пуст.
     * @return пустой или нет
     */
    public boolean dataEmpty() {
        return this.items.isEmpty();
    }

    /**
     * Метод, реализующий добавление заявок в хранилище.
     *
     * @param item новая заявка.
     * @return новая заявка.
     */
    public final Item add(final Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Метод заменяет указанную заявку из массива на новую.
     *
     * @param id   номер заменяемой заявки.
     * @param item новая заявка.
     */
    public final void replace(final String id, final Item item) {
        for (Item element : this.items) {
            if (element.getId().equals(id)) {
                int index = this.items.indexOf(element);
                item.setId(id);
                this.items.set(index, item);
                break;
            }
        }
    }

    /**
     * Метод удаляет значение из массива и смещает все
     * значения справа на одно значение влево.
     *
     * @param id индекс удаляемого значения.
     */
    public final void delete(final String id) {
        for (int index = 0; index < this.items.size(); index++) {
            if (this.items.get(index).getId().equals(id)) {
                this.items.remove(index);
                break;
            }
        }
    }

    /**
     * Метод удаляет все null в массиве.
     *
     * @return массив без null.
     */
    public final ArrayList<Item> findAll() {
            return this.items;
    }

    /**
     * Метод возвращает все элементы у которых
     * name совпадает со входящим значением.
     *
     * @param key искомое значение.
     * @return массив с подходящими значениями.
     */
    public final ArrayList<Item> findByName(final String key) {
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                result.add(item);
                break;
            }
        }
        return result;
    }

    /**
     * Метод находит элемент в массиве по id.
     *
     * @param id искомый id.
     * @return найденный элемент или null.
     */
    public final Item findById(final String id) {
        Item result = null;
        for (Item item : this.items) {
            if (item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание.
     * Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    public final String generateId() {
        return String.valueOf(RN.nextInt(100000));
    }
}
