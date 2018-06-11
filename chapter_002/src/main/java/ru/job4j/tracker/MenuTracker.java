package ru.job4j.tracker;

import java.util.Date;


public class MenuTracker {
    private Tracker tracker;
    private Input input;
    private UserAction[] actions = new UserAction[7];
    protected static Date date = new Date();
    public MenuTracker(Tracker tracker, Input input) {
        this.tracker = tracker;
        this.input = input;
    }
    public void fillActions() {
        this.actions[0] = this.new AddItem();
        this.actions[1] = new MenuTracker.ShowItems();
        this.actions[2] = this.new EditItem();
        this.actions[3] = this.new DeleteItem();
        this.actions[4] = this.new FindItemById();
        this.actions[5] = new FindItemsByName();
        this.actions[6] = new ExitProgram();
    }

    public void show() {
        for (UserAction action : this.actions) {
            System.out.println(action.info());
        }
    }

    public void select(int key) {
        if ((key > 0) && (key < 6) && (this.tracker.dataEmpty())) {
            System.out.println("Empty data");
        } else {
            this.actions[key].execute(this.tracker, this.input);
        }
    }

    public class AddItem implements UserAction {
        public int key() {
            return 0;
        }
        public void execute(Tracker tracker, Input input) {
            String name = input.ask("Item's name: ");
            String description = input.ask("Item's description: ");
            Item item = new Item(name, description, MenuTracker.date.getTime());
            tracker.add(item);
            System.out.println("New item with id: " + item.getId());
        }
        public String info() {
            return "0. Add new item";
        }
    }

    public static class ShowItems implements UserAction {
        public int key() {
            return 1;
        }
        public void execute(Tracker tracker, Input input) {
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
        public String info() {
            return "1. Show all items";
        }
    }
    public class EditItem implements UserAction {
        public int key() {
            return 2;
        }
        public void execute(Tracker tracker, Input input) {
            String answer = input.ask("Item's id: ");
            Item previous = tracker.findById(answer);
            if (previous == null) {
                System.out.println("Item with this id does't exist");
            } else {
                String name = input.ask("New item's name: ");
                String description = input.ask("New item's description: ");
                Item fresh = new Item(name, description, MenuTracker.date.getTime());
                tracker.replace(previous.getId(), fresh);
            }
        }
        public String info() {
            return "2. Edit item";
        }
    }

    public class DeleteItem implements UserAction {
        public int key() {
            return 3;
        }
        public void execute(Tracker tracker, Input input) {
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
        public String info() {
            return "3. Delete item";
        }
    }

    public class FindItemById implements UserAction {
        public int key() {
            return 4;
        }
        public void execute(Tracker tracker, Input input) {
            String id = input.ask("Item's id: ");
            Item result = tracker.findById(id);
            if (result == null) {
                System.out.println("Item with this id does't exist");
            } else {
                System.out.println("name: " + result.getName() + "   "
                        + "description: " + result.getDescription());
            }
        }
        public String info() {
            return "4. Find item by id";
        }
    }
}
class FindItemsByName implements UserAction {
    public int key() {
        return 5;
    }
    public void execute(Tracker tracker, Input input) {
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
    public String info() {
        return "5. Find items by name";
    }
}

class ExitProgram implements UserAction {
    public int key() {
        return 6;
    }
    public void execute(Tracker tracker, Input input) {
        StartUI.exit = true;
    }
    public String info() {
        return "6. Exit program";
    }
}
