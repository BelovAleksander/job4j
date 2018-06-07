package ru.job4j.tracker;

import java.util.*;
public class Tracker {
	/**
	* Массив для хранения заявок.
	*/
	private final Item[] items = new Item[100];
	/**
	* Указатель ячейки для новой заявки.
	*/
	private int position = 0;
	private static final Random RN = new Random();
    /**
     * Метод, реализующий добавление заявок в хранилище.
     * @param item новая заявка.
    */
	public Item add(Item item) {
		item.setId(this.generateId());
		this.items[position++] = item;
		return item;
	}
	/**
	* Метод заменяет указанную заявку из массива на новую.
	* @param id номер заменяемой заявки.
	* @param item новая заявка.
	*/
	public void replace(String id, Item item) {
		for (int index = 0; index != this.items.length; index++) {
			if (this.items[index].getId().equals(id)) {
				this.items[index] = item;
				break;
			}
		}
	}
	/**
     * Метод удаляет значение из массива и смещает все
     * значения справа на одно значение влево.
     * @param id индекс удаляемого значения.
     */
	public void delete(String id) {
		int flag = 0;
		for (int index = 0; index != this.items.length; index++) {
			if (this.items[index].getId().equals(id)) {
				this.items[index] = null;
				flag = index;
				break;
			}
		}
		System.arraycopy(this.items, flag + 1, this.items,
                flag, this.items.length - flag - 1);
	}

    /**
     * Метод удаляет все null в массиве.
     * @return массив без null.
     */
	public Item[] findAll() {
	    int flag = this.items.length;
	    for (int index = 0; index != this.items.length; index++) {
            if (this.items[index] == null) {
                flag = index;
                break;
            }
        }
        return Arrays.copyOf(this.items, flag);
    }

    /**
     * Метод возвращает все элементы у которых
     * name совпадает со входящим значением.
     * @param key искомое значение.
     * @return массив с подходящими значениями.
     */
    public Item[] findByName(String key) {
	    Item[] data = new Item[this.items.length];
	    int flag = 0;
	    for (Item item : this.items) {
	        if (item == null) {
	            break;
            } else if (item.getName().equals(key)) {
	            data[flag++] = item;
	            break;
            }
        }
        return Arrays.copyOf(data, flag);
    }

    /**
     * Метод находит элемент в массиве по id.
     * @param id искомый id.
     * @return найденный элемент или null.
     */
    public Item findById(String id) {
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
     * @return Уникальный ключ.
    */
	public String generateId() {
		return String.valueOf(System.currentTimeMillis() + RN.nextInt());
	}
}
