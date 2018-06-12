package ru.job4j.tracker;

/**
 * Класс отображает меню со всеми досупными в меню действиями
 * качестве внутренних классов.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.06.18
 */
public class MenuTracker {
    /**
     * хранилище заявок.
     */
    private Tracker tracker;
    /**
     * ввод данных.
     */
    private Input input;
    /**
     * массив доступных действий.
     */
    private UserAction[] actions = new UserAction[7];

    /**
     * Конструктор класса.
     * @param tracker хранилище заявок.
     * @param input ввод данных.
     */
    public MenuTracker(Tracker tracker, Input input) {
        this.tracker = tracker;
        this.input = input;
    }
    /**
     * указатель на конкретные классы.
     * @param ui ссылка на основной цикл. Нужна для того,
     *           чтобы его прервать.
     */
    public final void fillActions(final StartUI ui) {
        this.actions[0] = this.new AddItem();
        this.actions[1] = new MenuTracker.ShowItems();
        this.actions[2] = this.new EditItem();
        this.actions[3] = this.new DeleteItem();
        this.actions[4] = this.new FindItemById();
        this.actions[5] = new FindItemsByName();
        this.actions[6] = new ExitProgram(ui);
    }
    /**
     * вывод элементов меню.
     */
    public final void show() {
        for (UserAction action : this.actions) {
            System.out.println(action.info());
        }
    }
    /**
     * метод-указатель на execute внутренних классов.
     * @param key выбор пользователя.
     */
    public final void select(final int key) {
        if ((key > 0) && (key < 6) && (this.tracker.dataEmpty())) {
            System.out.println("Empty data");
        } else {
            this.actions[key].execute(this.tracker, this.input);
        }
    }
    /**
     * Класс, обрабатывающий добавление заявки.
     */
    public class AddItem implements UserAction {
        /**
         * предоставляет индекс в главном меню.
         * @return индекс.
         */
        public final int key() {
            return 0;
        }

        /**
         * Метод содержит действия, требуемые для обработки
         * запроса пользователя.
         * @param tracker хранилище заявок.
         * @param input ввод данных.
         */
        public final void execute(final Tracker tracker, final Input input) {
            String name = input.ask("Item's name: ");
            String description = input.ask("Item's description: ");
            Item item = new Item(name, description);
            tracker.add(item);
            System.out.println("New item with id: " + item.getId());
        }
        /**
         * Подзаголовок в главном меню.
         * @return строка.
         */
        public final String info() {
            return "0. Add new item";
        }
    }
    /**
     * Класс, обрабатывающий запрос на вывод всех заявок.
     */
    public static class ShowItems implements UserAction {
        /**
         * предоставляет индекс в главном меню.
         * @return индекс.
         */
        public final int key() {
            return 1;
        }
        /**
         * Метод содержит действия, требуемые для обработки
         * запроса пользователя.
         * @param tracker хранилище заявок.
         * @param input ввод данных.
         */
        public final void execute(final Tracker tracker, final Input input) {
            Item[] data = tracker.findAll();
            if (data.length == 0) {
                System.out.println("Empty data");
            } else {
                for (Item item : data) {
                    System.out.println("name: " + item.getName() + "   "
                            + "description: " + item.getDescription());
                    System.out.println("---------------------");
                }
            }
        }
        /**
         * Подзаголовок в главном меню.
         * @return строка.
         */
        public final String info() {
            return "1. Show all items";
        }
    }
    /**
     * Класс, обрабатывающий запрос на редактирование заявки.
     */
    public class EditItem implements UserAction {
        /**
         * предоставляет индекс в главном меню.
         * @return индекс.
         */
        public final int key() {
            return 2;
        }
        /**
         * Метод содержит действия, требуемые для обработки
         * запроса пользователя.
         * @param tracker хранилище заявок.
         * @param input ввод данных.
         */
        public final void execute(final Tracker tracker, final Input input) {
            String answer = input.ask("Item's id: ");
            Item previous = tracker.findById(answer);
            if (previous == null) {
                System.out.println("Item with this id does't exist");
            } else {
                String name = input.ask("New item's name: ");
                String description = input.ask("New item's description: ");
                Item fresh = new Item(name, description);
                tracker.replace(previous.getId(), fresh);
            }
        }

        /**
         * Подзаголовок в главном меню.
         * @return строка.
         */
        public final String info() {
            return "2. Edit item";
        }
    }
    /**
     * Класс, обрабатывающий запрос на удаление заявки.
     */
    public class DeleteItem implements UserAction {
        /**
         * предоставляет индекс в главном меню.
         * @return индекс.
         */
        public final int key() {
            return 3;
        }

        /**
         * Метод содержит действия, требуемые для обработки
         * запроса пользователя.
         * @param tracker хранилище заявок.
         * @param input ввод данных.
         */
        public final void execute(final Tracker tracker, final Input input) {
            String answer = input.ask(
                    "Item's id: ");
            Item previous = tracker.findById(answer);
            if (previous == null) {
                System.out.println("Item with this id does't exist");
            } else {
                tracker.delete(previous.getId());
                System.out.println("Item deleted.");
            }
        }

        /**
         * Подзаголовок в главном меню.
         * @return строка.
         */
        public final String info() {
            return "3. Delete item";
        }
    }
    /**
     * Класс, обрабатывающий запрос на поиск заявки по id.
     */
    public class FindItemById implements UserAction {
        /**
         * предоставляет индекс в главном меню.
         * @return индекс.
         */
        public final int key() {
            return 4;
        }

        /**
         * Метод содержит действия, требуемые для обработки
         * запроса пользователя.
         * @param tracker хранилище заявок.
         * @param input ввод данных.
         */
        public final void execute(final Tracker tracker, final Input input) {
            String id = input.ask("Item's id: ");
            Item result = tracker.findById(id);
            if (result == null) {
                System.out.println("Item with this id does't exist");
            } else {
                System.out.println("name: " + result.getName() + "   "
                        + "description: " + result.getDescription());
            }
        }

        /**
         * Подзаголовок в главном меню.
         * @return строка.
         */
        public final String info() {
            return "4. Find item by id";
        }
    }
}
/**
 * Класс, обрабатывающий запрос на поиск заявки по имени.
 */
class FindItemsByName implements UserAction {
    /**
     * предоставляет индекс в главном меню.
     * @return индекс.
     */
    public final int key() {
        return 5;
    }

    /**
     * Метод содержит действия, требуемые для обработки
     * запроса пользователя.
     * @param tracker хранилище заявок.
     * @param input ввод данных.
     */
    public final void execute(final Tracker tracker, final Input input) {
        String answer = input.ask("Item's name: ");
        Item[] data = tracker.findByName(answer);
        if (data.length == 0) {
            System.out.println("Item with this name doesn't exist");
        }
        for (Item item : data) {
            System.out.println("name: " + item.getName() + "   "
                    + "description: " + item.getDescription());
            System.out.println("----------------------------------------");
        }
    }

    /**
     * Подзаголовок в главном меню.
     * @return строка.
     */
    public final String info() {
        return "5. Find items by name";
    }
}
/**
 * Класс, обрабатывающий запрос на выход из программы.
 */
class ExitProgram implements UserAction {
    /**
     * ссылка на действующий основной цикл.
     */
    private final StartUI ui;

    /**
     * Конструктор класса
     * @param ui ссылка на основной цикл.
     */
    public ExitProgram(final StartUI ui) {
        this.ui = ui;
    }

    /**
     * предоставляет индекс в главном меню.
     * @return индекс.
     */
    public final int key() {
        return 6;
    }

    /**
     * Метод содержит действия, требуемые для обработки
     * запроса пользователя.
     * @param tracker хранилище заявок.
     * @param input ввод данных.
     */
    public final void execute(final Tracker tracker, final Input input) {
        ui.stop();
    }

    /**
     * Подзаголовок в главном меню.
     * @return строка.
     */
    public final String info() {
        return "6. Exit program";
    }
}
