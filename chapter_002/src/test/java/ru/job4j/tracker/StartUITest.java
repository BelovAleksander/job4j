package ru.job4j.tracker;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public class StartUITest {
    private Tracker tracker = new Tracker();
    private final PrintStream stdOut = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String menu = new StringBuilder()
            .append("0. Add new item\\r\\n")
            .append("1. Show all items\\r\\n")
            .append("2. Edit item\\r\\n")
            .append("3. Delete item\\r\\n")
            .append("4. Find item by id\\r\\n")
            .append("5. Find items by name\\r\\n")
            .append("6. Exit program\\r\\n")
            .toString();
    private final String separator = "---------------------\\r\\n";

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutputAndCleanTracker() {
        System.out.println("execute after method");
        System.setOut(stdOut);
        this.tracker = null;
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUI(input, this.tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(this.tracker.findAll()[0].getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenShowAllItems() {
        Item item1 = this.tracker.add(new Item("test1", "desc1", 123L));
        Item item2 = this.tracker.add(new Item("test2", "desc2", 1234L));
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, this.tracker).init();
        assertThat(new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("name: test1   description: desc1\\r\\n")
                                .append(separator)
                                .append("name: test2   description: desc2\\r\\n")
                                .append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenTryFindByIdItem2ThenFind() {
        Item item = this.tracker.add(new Item("test", "desc", 123L));
        Input input = new StubInput(new String[]{"4", item.getId(), "6"});
        new StartUI(input, this.tracker).init();
        assertThat(new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("name: test   description: desc\\r\\n")
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        //Напрямую добавляем заявку
        Item item = this.tracker.add(new Item("test name", "desc", 123L));
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"1", item.getId(), "test name", "desc", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, this.tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(this.tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenEditItem2ThenDescriptionChanged() {
        Item item1 = this.tracker.add(new Item("test1", "description1", 123L));
        Item item2 = this.tracker.add(new Item("test2", "description2", 1234L));
        Input input = new StubInput(new String[]{"2", "1", "test2", "changed description", "6"});
        new StartUI(input, this.tracker).init();
        assertThat(this.tracker.findById(item2.getId()).getDescription(), is("changed description"));
    }

    @Test
    public void whenDeleteItem2ThenFindAllLengthEquals1() {
        Item item1 = this.tracker.add(new Item("test1", "description1", 123L));
        Item item2 = this.tracker.add(new Item("test2", "description2", 1234L));
        Input input = new StubInput(new String[]{"3", "1", "6"});
        new StartUI(input, this.tracker).init();
        assertThat(this.tracker.findAll().length, is(1));
    }
}
