package ru.job4j.tests;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 18.06.18
 */

public class CoffeeMachineTest {

    @Test
    public void whenValue50AndPrice35() {
        CoffeeMachine coffee = new CoffeeMachine();
        int[] result = coffee.changes(50, 35);
        assertThat(result, is(
                new int[]{10, 5}
                )
        );
    }

    @Test
    public void whenValue50AndPrice2() {
        CoffeeMachine sugar = new CoffeeMachine();
        int[] result = sugar.changes(50, 2);
        assertThat(result, is(
                new int[]{10, 10, 10, 10, 5, 2, 1}
                )
        );
    }
}
