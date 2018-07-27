package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.07.2018
 */
public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        try (Tracker tracker = new Tracker()) {
            Item item = new Item("test1", "testDescription");
            tracker.add(item);
            assertThat(tracker.findAll().get(0).equals(item), is(true));
        }
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        try (Tracker tracker = new Tracker()) {
            Item previous = new Item("test1", "testDescription");
            // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
            tracker.add(previous);
            // Создаем новую заявку.
            Item next = new Item("test2", "testDescription2");
            // Обновляем заявку в трекере.
            tracker.replace(previous.getId(), next);
            // Проверяем, что заявка с таким id имеет новые имя test2.
            assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
        }
    }

    @Test
    public void whenFindByNameThenFindItem2() {
        try (Tracker tracker = new Tracker()) {
            Item item1 = new Item("test1", "testDescription");
            tracker.add(item1);
            Item item2 = new Item("test2", "testDescription2");
            tracker.add(item2);
            ArrayList<Item> data = tracker.findByName("test2");
            Item result = data.get(0);
            assertThat(result, is(item2));
        }
    }

    @Test
    public void whenFindByIdThenFindItem2() {
        try (Tracker tracker = new Tracker()) {
            Item item1 = new Item("test1", "testDescription");
            tracker.add(item1);
            Item item2 = new Item("test2", "testDescription2");
            tracker.add(item2);
            Item result = tracker.findById(item2.getId());
            assertThat(result, is(item2));
        }
    }

    @Test
    public void whenDeleteItem2ThenDataLengthIs0() {
        try (Tracker tracker = new Tracker()) {
            Item item1 = new Item("test1", "testDescription");
            tracker.add(item1);
            Item item2 = new Item("test2", "testDescription2");
            tracker.add(item2);
            ArrayList<Item> data = tracker.findByName("test2");
            Item result = data.get(0);
            tracker.delete(result.getId());
            assertThat(tracker.findByName(result.getName()).size(), is(0));
        }
    }

    @Test
    public void whenFindAllThenWithoutNull() {
        try (Tracker tracker = new Tracker()) {
            Item item1 = new Item("test1", "testDescription");
            tracker.add(item1);
            Item item2 = new Item("test2", "testDescription2");
            tracker.add(item2);
            for (Item i : tracker.findAll()) {
                System.out.println(i);
            }
            assertThat(tracker.findAll().size(), is(2));
        }
    }
}
