package ru.job4j.tracker;

/**
 * Интерфейс для пользовательских действий.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com).
 * @since 11.06.18
 */
public interface UserAction {
    int key();
    void execute(Tracker tracker, Input input);
    String info();
}
