package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.06.18
 */

public class DynamicLinkedListTest {

    @Test
    public void whenAddThenSuccess() {
        DynamicLinkedList<Integer> container = new DynamicLinkedList<>();
        container.addLast(1);
        container.addLast(2);
        container.addLast(3);
        container.addLast(4);
        container.addLast(5);
        assertThat(container.get(1), is(2));
    }

    @Test
    public void whenIteratorNextThenReturnElementWithIndex0() {
        DynamicLinkedList<Integer> container = new DynamicLinkedList<>();
        container.addLast(1);
        container.addLast(2);
        container.addLast(3);
        Iterator<Integer> it = container.iterator();
        assertThat(it.next(), is(1));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenUseIteratorAndAddElementThenException() {
        DynamicLinkedList<Integer> container = new DynamicLinkedList<>();
        container.addLast(1);
        container.addLast(2);
        Iterator<Integer> it = container.iterator();
        it.next();
        container.addLast(10);
        it.next();
    }
}
