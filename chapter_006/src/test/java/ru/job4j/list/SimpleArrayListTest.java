package ru.job4j.list;

import org.junit.Test;

import org.junit.Before;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;


public class SimpleArrayListTest {

    private SimpleArrayList<Integer> list;

    @Before

    public void beforeTest() {
        list = new SimpleArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test

    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenDeleteOneElementThenSizeIs2() {
        list.delete();
        assertThat(list.get(0), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenListIsEmptyAndDeleteThenException() {
        list.delete();
        list.delete();
        list.delete();
        list.delete();
    }
}
