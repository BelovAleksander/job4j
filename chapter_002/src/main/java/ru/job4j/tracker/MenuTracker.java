package ru.job4j.tracker;

/**
 * Класс отображает меню со всеми досупными в меню действиями
 * качестве внутренних классов.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.06.18
 */
public class MenuTracker {
    private final static int ACTIONS = 7;
    /**
     * хранилище заявок.
     */
    private Tracker tracker;
    /**
     * ввод данных.
     */
    private Input input;
    /**
     * счетчик
     */
    private int position = 0;
    /**
     * массив доступных действий.
     */
    private UserAction[] actions = new UserAction[ACTIONS];

    /**
     * Конструктор класса.
     * @param tracker хранилище заявок.
     * @param input ввод данных.
     */
    public MenuTracker(final Tracker tracker, final Input input) {
        this.tracker = tracker;
        this.input = input;
    }
    /**
     * указатель на конкретные классы.
     * @param ui ссылка на основной цикл. Нужна для того,
     *           чтобы его прервать.
     */
    public final void fillActions(final StartUI ui) {
        this.actions[position++] = this.new AddItem("Add new item.", 0, ui);
        this.actions[position++] = new MenuTracker.ShowItems("Show all items.", 1, ui);
        this.actions[position++] = this.new EditItem("Edit item.", 2, ui);
        this.actions[position++] = this.new DeleteItem("Delete item.", 3, ui);
        this.actions[position++] = this.new FindItemById("Find item by id.", 4, ui);
        this.actions[position++] = new FindItemsByName("Find items by name.", 5, ui);
        this.actions[position++] = new ExitProgram("Exit program.", 6, ui);
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
    public class AddItem extends BaseAction {
        /**
         * Конструктор
         * @param name подзаголовок в меню
         * @param key номер в меню
         */
        public AddItem(String name, int key, StartUI ui) {
            super(name, key, ui);
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
    }
    /**
     * Класс, обрабатывающий запрос на вывод всех заявок.
     */
    public static class ShowItems extends BaseAction {
        /**
         *
         * @param name
         * @param key
         */
        public ShowItems(String name, int key, StartUI ui) {
            super(name, key, ui);
        }
        /**
         * Метод содержит действия, требуемые для обработки
         * запроса пользователя.
         * @param tracker хранилище заявок.
         * @param input ввод данных.
         */
        public final void execute(final Tracker tracker, final Input input) {
            Item[] data = tracker.findAll();
            for (Item item : data) {
                System.out.println("name: " + item.getName() + "   "
                        + "description: " + item.getDescription());
                System.out.println("---------------------");
            }
        }
    }
    /**
     * Класс, обрабатывающий запрос на редактирование заявки.
     */
    public class EditItem extends BaseAction {
        /**
         *
         * @param name
         * @param key
         */
        public EditItem(String name, int key, StartUI ui) {
            super(name, key, ui);
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
    }
    /**
     * Класс, обрабатывающий запрос на удаление заявки.
     */
    public class DeleteItem extends BaseAction {
        public DeleteItem(String name, int key, StartUI ui) {
            super(name, key, ui);
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
    }
    /**
     * Класс, обрабатывающий запрос на поиск заявки по id.
     */
    public class FindItemById extends BaseAction {
        public FindItemById(String name, int key, StartUI ui) {
            super(name, key, ui);
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
    }
}
/**
 * Класс, обрабатывающий запрос на поиск заявки по имени.
 */
class FindItemsByName extends BaseAction {
    public FindItemsByName(String name, int key, StartUI ui) {
        super(name, key, ui);
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
}
/**
 * Класс, обрабатывающий запрос на выход из программы.
 */
class ExitProgram extends BaseAction {
    /**
     * Конструктор класса.
     * @param ui ссылка на основной цикл.
     */
    ExitProgram(final String name, final int key, final StartUI ui) {
        super(name, key, ui);
    }

    /**
     * Метод содержит действия, требуемые для обработки
     * запроса пользователя.
     * @param tracker хранилище заявок.
     * @param input ввод данных.
     */
    public final void execute(final Tracker tracker, final Input input) {
        super.stop();
    }
}
