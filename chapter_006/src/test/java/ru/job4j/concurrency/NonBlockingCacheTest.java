package ru.job4j.concurrency;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class NonBlockingCacheTest {

    @Test
    public void whenUpdateElementThenValueChange() {
        NonBlockingCache cache = new NonBlockingCache();
        cache.add(new Base(1, 10));
        cache.update(1, 15);
        assertThat(cache.get(1), is(new Base(1, 15)));
    }

    @Test
    public void whenUpdateElementThenVersionChange() {
        NonBlockingCache cache = new NonBlockingCache();
        cache.add(new Base(1, 10));
        cache.update(1, 15);
        assertThat(cache.get(1).getVersion(), is(1));
    }

    /**
     * @Test(expected = OptimisticException.class)
    public void whenManyThreadsTriesToUpdateElementThenThrowsOptimisticException() {
        NonBlockingCache cache = new NonBlockingCache();
        cache.add(new Base(1 ,10));
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    cache.update(1, 10 + finalI);
                }
            }.start();
        }
    }
    */
}
