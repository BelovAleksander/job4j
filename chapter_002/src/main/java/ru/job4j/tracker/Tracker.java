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
    private final Item[] items = new Item[100];
    /**
     * Указатель ячейки для новой заявки.
     */
    private int position = 0;
    /**
     * Хранит рандомное значение.
     */
    private static final Random RN = new Random();

    /**
     * Метод, реализующий добавление заявок в хранилище.
     *
     * @param item новая заявка.
     * @return новая заявка.
     */
    public final Item add(final Item item) {
        item.setId(this.generateId());
        this.items[this.position++] = item;
        return item;
    }

    /**
     * Метод заменяет указанную заявку из массива на новую.
     *
     * @param id   номер заменяемой заявки.
     * @param item новая заявка.
     */
    public final void replace(final String id, final Item item) {
        for (int index = 0; index != this.items.length; index++) {
            if (this.items[index].getId().equals(id)) {
                item.setId(id);
                this.items[index] = item;
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
        for (int index = 0; index != this.items.length; index++) {
            if (this.items[index].getId().equals(id)) {
                this.items[index] = null;
                System.arraycopy(this.items, index + 1, this.items,
                        index, this.items.length - index - 1);
                this.position--;
                System.out.println(this.position);
                break;
            }
        }

    }

    /**
     * Метод удаляет все null в массиве.
     *
     * @return массив без null.
     */
    public final Item[] findAll() {
        Item[] result = null;
        for (int index = 0; index != this.position; index++) {
            if (this.position - index == 1) {
                result = Arrays.copyOf(this.items, index + 1);
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает все элементы у которых
     * name совпадает со входящим значением.
     *
     * @param key искомое значение.
     * @return массив с подходящими значениями.
     */
    public final Item[] findByName(final String key) {
        int flag = 0;
        Item[] data = new Item[this.position];
        for (int index = 0; index != this.position; index++) {
            if (this.items[index].getName().equals(key)) {
                data[flag++] = this.items[index];
                break;
            }
        }
        return Arrays.copyOf(data, flag);
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
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
}
