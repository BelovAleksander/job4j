package ru.job4j.xml;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.07.18
 */

public class ManipulatorTest {
    @Test
    public void whenCountIs1000000ThenTimeLowerThen5minutes() {
        long start = System.currentTimeMillis();
        Manipulator manipulator = new Manipulator(1000);  // instead 1000000
        manipulator.init();
        long stop = System.currentTimeMillis();
        assertThat((stop - start) < 300000, is(true));
    }
}
