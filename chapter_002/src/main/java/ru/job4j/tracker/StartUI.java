package ru.job4j.tracker;

import java.util.Date;

import static java.lang.Integer.parseInt;

public class StartUI {
    private final ConsoleInput input;
    private final Tracker tracker;
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
    public StartUI(final ConsoleInput input, final Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Метод предоставляет пользователю навигацию по главному меню.
     *
     * @return выбранное действие.
     */
    public final String mainMenuChoice() {
        System.out.println("0. Add new item");
        System.out.println("1. Show all items");
        System.out.println("2. Edit item");
        System.out.println("3. Delete item");
        System.out.println("4. Find item by id");
        System.out.println("5. Find items by name");
        System.out.println("6. Exit program");
        String answer = this.input.ask("Select action: ");
        return answer;
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
        for (Item item : data) {
            System.out.println("name: " + item.getName() + "   "
                    + "description: " + item.getDescription());
            System.out.println("---------------------");
        }
    }

    /**
     * Позволяет редактировать выбранную заметку.
     */
    public final void editItem() {
        showAllItems();
        String answer = this.input.ask(
                "Index of item that you want to edit: ");
        Item previous = this.tracker.findAll()[parseInt(answer)];
        String name = this.input.ask("New item's name: ");
        String description = this.input.ask("New item's description: ");
        Item fresh = new Item(name, description, date.getTime());
        this.tracker.replace(previous.getId(), fresh);
    }

    /**
     * Позволяет удалить выбранную заявку.
     */
    public final void deleteItem() {
        showAllItems();
        String answer = this.input.ask(
                "Index of item that you want to delete: ");
        Item previous = this.tracker.findAll()[parseInt(answer)];
        this.tracker.delete(previous.getId());
        System.out.println("Item deleted.");
    }

    /**
     * Позволяет найти заявку по id.
     */
    public final void findItemById() {
        String id = this.input.ask("Item's id: ");
        Item result = this.tracker.findById(id);
        System.out.println("name: " + result.getName() + "   "
                + "description: " + result.getDescription());
    }

    /**
     * Позволяет найти заявки с заданным именем.
     */
    public final void findItemsByName() {
        String answer = this.input.ask("Item's name: ");
        Item[] data = this.tracker.findByName(answer);
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
            switch (mainMenuChoice()) {
                case ADD:
                    createNewItem();
                    break;
                case SHOW:
                    showAllItems();
                    break;
                case EDIT:
                    editItem();
                    break;
                case DELETE:
                    deleteItem();
                    break;
                case FIND_ID:
                    findItemById();
                    break;
                case FIND_NAME:
                    findItemsByName();
                    break;
                case EXIT:
                    exit = true;
                    break;
                default:

            }
        }
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
