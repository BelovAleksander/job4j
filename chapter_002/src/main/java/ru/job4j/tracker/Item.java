package ru.job4j.tracker;

import java.util.Objects;

/**
 * @author whiterabbit.nsk
 * @since 08.06.2018
 * Класс Item представляет собой фабрику заявок.
 */
public class Item {
	/**
	 * уникальный идентификатор.
	 */
	private String id;
	/**
	 * имя заявки.
	 */
	private  String name;
	/**
	 * описание заявки.
	 */
	private String description;

	/**
	 * Конструктор класса.
	 * @param name имя заявки
	 * @param description описание заявки
	 */
	public Item(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	public Item(final String id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

    /**
     * возвращает имя.
      * @return имя
     */
	public final String getName() {
		return this.name;
	}

    /**
     * возвращает описание
     * @return описание
     */
    public final String getDescription() {
		return description;
	}

    /**
     * возвращает id
     * @return id
     */
	public final String getId() {
		return this.id;
	}

    /**
     * устанавливает id
     * @param id уникальный идентификационный номер
     */
	public final void setId(final String id) {
		this.id = id;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description);
    }
}
