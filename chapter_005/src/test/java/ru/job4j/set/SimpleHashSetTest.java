package ru.job4j.set;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.06.18
 */

public class SimpleHashSetTest {
    @Test
    public void whenAddElementThenShowThatContainsIt() {
        SimpleHashSet set = new SimpleHashSet(7);
        set.add(112);
        set.add(456);
        assertThat(set.contains(112), is(true));
    }

    @Test
    public void whenSize7AndAdd6ElementsThenSize17() {
        SimpleHashSet set = new SimpleHashSet(7);
        set.add(112);
        set.add(456);
        set.add(34);
        set.add(234);
        set.add(2324);
        set.add(2314);
        assertThat(set.getSize(), is(17));
    }

    @Test
    public void whenRemoveElementThenDoesntContainsIt() {
        SimpleHashSet set = new SimpleHashSet(7);
        set.add(112);
        set.add(456);
        set.remove(112);
        assertThat(set.contains(112), is(false));
    }
}
