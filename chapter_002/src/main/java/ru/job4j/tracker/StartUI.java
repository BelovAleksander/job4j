package ru.job4j.tracker;

import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Является пусковым классом программы.
 * Содержит main метод  .
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public class StartUI {
    /**
     * Ввод данных.
     */
    private final Input input;
    /**
     * Хранилище заявок.
     */
    private final Tracker tracker;
    /**
     * Текущая дата.
     */
    private final Date date = new Date();

    public static boolean exit = false;
    /**
     * Конструктор класса.
     *
     * @param input   ввод данных.
     * @param tracker хранилище завок.
     */
    public StartUI(final Input input, final Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * Основной цикл программы.
     */
    public final void init() {
        MenuTracker menu = new MenuTracker(this.tracker, this.input);
        menu.fillActions();
        while (!this.exit) {
            menu.show();
            int key = Integer.parseInt(this.input.ask("Select action: "));
            menu.select(key);
        }
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
