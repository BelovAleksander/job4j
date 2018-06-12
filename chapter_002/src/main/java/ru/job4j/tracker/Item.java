package ru.job4j.tracker;

/**
 * @author whiterabbit.nsk
 * @since 08.06.2018
 * Класс Item представляет собой фабрику заявок.
 */
public class Item {
	private String id;
	private  String name;
	private String description;

	public Item() {
	}
	public Item(final String name, final String description) {
		this.name = name;
		this.description = description;
		}
	public final String getName() {
		return this.name;
	}
    public final String getDescription() {
		return description;
	}
	public final String getId() {
		return this.id;
	}
	public final void setId(final String id) {
		this.id = id;
	}
}
