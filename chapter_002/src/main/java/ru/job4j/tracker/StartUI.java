package ru.job4j.tracker;

import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Является главным классом программы.
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
    /**
     * Константа для добавления заявки.
     */
    private static final String ADD = "0";
    /**
     * Константа для вывода на экран всех заявок.
     */
    private static final String SHOW = "1";
    /**
     * Константа для правки заявки.
     */
    private static final String EDIT = "2";
    /**
     * Константа для удаления заявки.
     */
    private static final String DELETE = "3";
    /**
     * Константа для поиска заявки по id.
     */
    private static final String FIND_ID = "4";
    /**
     * Константа для вывода на экран заявок с
     * заданным именем.
     */
    private static final String FIND_NAME = "5";
    /**
     * Константа для выхода.
     */
    private static final String EXIT = "6";

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
     * Метод предоставляет пользователю навигацию по главному меню.
     *
     * @return выбранное действие.
     */
    public final void mainMenu() {
        System.out.println("0. Add new item");
        System.out.println("1. Show all items");
        System.out.println("2. Edit item");
        System.out.println("3. Delete item");
        System.out.println("4. Find item by id");
        System.out.println("5. Find items by name");
        System.out.println("6. Exit program");
    }

    /**
     * Метод создает новую заявку.
     */
    public final void createNewItem() {
        String name = this.input.ask("Item's name: ");
        String description = this.input.ask("Item's description: ");
        Item item = new Item(name, description, this.date.getTime());
        this.tracker.add(item);
        System.out.println("New item with id: " + item.getId());
    }

    /**
     * Выводит на экран все заявки.
     */
    public final void showAllItems() {
        Item[] data = this.tracker.findAll();
        if (data.length == 0) {
            System.out.println("Empty data");
        }
        else {
            for (Item item : data) {
                System.out.println("name: " + item.getName() + "   "
                        + "description: " + item.getDescription());
                System.out.println("---------------------");
            }
        }
    }

    /**
     * Позволяет редактировать выбранную заметку.
     */
    public final void editItem() {
        String answer = this.input.ask("Item's id: ");
        Item previous = this.tracker.findById(answer);
        if (previous == null) {
            System.out.println("Item with this id does't exist");
        }
        else {
            String name = this.input.ask("New item's name: ");
            String description = this.input.ask("New item's description: ");
            Item fresh = new Item(name, description, date.getTime());
            this.tracker.replace(previous.getId(), fresh);
        }
    }

    /**
     * Позволяет удалить выбранную заявку.
     */
    public final void deleteItem() {
        String answer = this.input.ask(
                "Item's id: ");
        Item previous = this.tracker.findById(answer);
        if (previous == null) {
            System.out.println("Item with this id does't exist");
        } else {
            this.tracker.delete(previous.getId());
            System.out.println("Item deleted.");
        }
    }

    /**
     * Позволяет найти заявку по id.
     */
    public final void findItemById() {
        String id = this.input.ask("Item's id: ");
        Item result = this.tracker.findById(id);
        if (result == null) {
            System.out.println("Item with this id does't exist");
        } else {
            System.out.println("name: " + result.getName() + "   "
                    + "description: " + result.getDescription());
        }
    }

    /**
     * Позволяет найти заявки с заданным именем.
     */
    public final void findItemsByName() {
        String answer = this.input.ask("Item's name: ");
        Item[] data = this.tracker.findByName(answer);
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
     * Основной цикл программы.
     */
    public final void init() {
        boolean exit = false;
        while (!exit) {
            mainMenu();
            String answer = input.ask("Select action: ");
            if (answer.equals(ADD)) {
                createNewItem();
            } else if ((answer.equals(SHOW)) && (!tracker.dataEmpty())) {
                showAllItems();
            } else if ((answer.equals(EDIT)) && (!tracker.dataEmpty())) {
                editItem();
            } else if ((answer.equals(DELETE)) && (!tracker.dataEmpty())) {
                deleteItem();
            } else if ((answer.equals(FIND_ID)) && (!tracker.dataEmpty())) {
                findItemById();
            } else if ((answer.equals(FIND_NAME)) && (!tracker.dataEmpty())) {
                findItemsByName();
            } else if (answer.equals(EXIT)) {
                exit = true;
            }
        }
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
