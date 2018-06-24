package ru.job4j.generic;

/**
 * Класс-шаблон для создания классов User и Role.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */
public abstract class Base {
    /**
     * id
     */
    private final String id;

    protected Base(final String id) {
        this.id = id;
    }

    /**
     * Геттер для id.
     * @return id
     */
    public String getId() {
        return id;
    }
}
