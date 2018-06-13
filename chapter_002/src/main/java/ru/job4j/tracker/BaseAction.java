package ru.job4j.tracker;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.06.18
 */
public abstract class BaseAction implements UserAction {
    private final String name;
    private final int key;

    public BaseAction(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public int key() {
        return this.key;
    }

    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
