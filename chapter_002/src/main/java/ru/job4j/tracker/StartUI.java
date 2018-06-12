package ru.job4j.tracker;

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
     * Последовательность допустимых значений в меню.
     */
    private int[] range = new int[]{0, 1, 2, 3, 4, 5, 6};
    /**
     * Флаг выхода из цикла.
     */
    private boolean exit = false;
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
     * Остановка основного цикла программы.
     */
    public void stop() {
        this.exit = true;
    }
    /**
     * Основной цикл программы.
     */
    public final void init() {
        MenuTracker menu = new MenuTracker(this.tracker, this.input);
        menu.fillActions(this);
        while (!this.exit) {
            menu.show();
            menu.select(input.ask("Select action: ", range));
        }
    }

    public static void main(String[] args) {
        new StartUI(new ValidateInput(
                new ConsoleInput()
        ), new Tracker())
                .init();
    }
}
