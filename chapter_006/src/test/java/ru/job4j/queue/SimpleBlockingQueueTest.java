package ru.job4j.queue;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 13.07.2018
 */

public class SimpleBlockingQueueTest {
    SimpleBlockingQueue queue = new SimpleBlockingQueue();
    Interaction action = new Interaction();
    @Test
    public void whenOneConsumerAndThreeProducers() {
        Interaction.Consumer consumer = action.new Consumer<>(5);
        Thread cons = new Thread(consumer, "consumer");
        Thread prod1 = new Thread(action.new Producer(1, 2), "producer");
        Thread prod2 = new Thread(action.new Producer(2, 1), "producer");
        Thread prod3 = new Thread(action.new Producer(3, 2), "producer");
        cons.start();
        prod1.start();
        prod2.start();
        prod3.start();
        try {
            cons.join();
            prod1.join();
            prod2.join();
            prod3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer result = 0;
        for (Object el :  consumer.getValues()) {
            result += (Integer) el;
        }

        assertThat(result, is(1 + 1 + 2 + 3 + 3));
    }
}