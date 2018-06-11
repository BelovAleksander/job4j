package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public class StartUITest {
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.findAll()[0].getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc", 123L));
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"1", item.getId(), "test name", "desc", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenEditItem2ThenDescriptionChanged() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test1", "description1", 123L));
        Item item2 = tracker.add(new Item("test2", "description2", 1234L ));
        Input input = new StubInput(new String[]{"2", "1", "test2", "changed description", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item2.getId()).getDescription(), is("changed description"));
    }

    @Test
    public void whenDeleteItem2ThenFindAllLengthEquals1() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test1", "description1", 123L));
        Item item2 = tracker.add(new Item("test2", "description2", 1234L ));
        Input input = new StubInput(new String[]{"3", "1", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().length, is(1));
    }
}
