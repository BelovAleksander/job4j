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
	private long create;

	public Item() {
	}
	public Item(final String name, final String description, final long create) {
		this.name = name;
		this.description = description;
		this.create = create;
	}
	public final String getName() {
		return this.name;
	}
    public final String getDescription() {
		return description;
	}
	public final long getCreate() {
		return create;
	}
	public final String getId() {
		return this.id;
	}
	public final void setId(final String id) {
		this.id = id;
	}
}
