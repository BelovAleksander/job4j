package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.06.18
 */

public class DynamicArrayListTest {
    DynamicArrayList<Integer> container = new DynamicArrayList<>(3);

    @Before
    public void setUp() {

        container.add(5);
        container.add(6);
        container.add(7);
    }

    @Test
    public void whenSizeIs3AndWePutElementsThenSizeIs6() {

        assertThat(container.getSize(), is(6));
    }

    @Test
    public void whenGetIndex1Then6() {
        assertThat(container.get(1), is(6));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenUseIteratorAndAddElementThenException() {
        Iterator<Integer> it = container.iterator();
        it.next();
        container.add(10);
        it.next();
    }
}