package ru.job4j.test;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExchangeTest {

    @Test
    public void whenAddItemThenReturnTrue() {
        Exchange ex = new Exchange();
        ex.addIssuer("HP");
        assertThat(ex.addItem("HP", true, true, 10D, 10), is(true));
    }

    @Test
    public void whenAddOneItemAndDeleteItThenPrintNothing() {
        Exchange ex = new Exchange();
        ex.addIssuer("HP");
        ex.addItem("HP", true, true, 10D, 10);
        ex.addItem("HP", false, true, 10D, 10);
        Glass glass = ex.getIssuer(0);
        assertThat(glass.toString(), is(""));
    }

    @Test
    public void whenAddOneItemAndAddOppositeItemThenPrintNothing() {
        Exchange ex = new Exchange();
        ex.addIssuer("HP");
        ex.addItem("HP", true, true, 10D, 10);
        ex.addItem("HP", true, false, 10D, 10);
        Glass glass = ex.getIssuer(0);
        assertThat(glass.toString(), is(""));
    }

    @Test
    public void whenAddTwoItemsWithSamePriceAndSameValuesThenUniteThem() {
        Exchange ex = new Exchange();
        ex.addIssuer("HP");
        ex.addItem("HP", true, true, 10D, 10);
        ex.addItem("HP", true, true, 10D, 10);
        Glass glass = ex.getIssuer(0);
        assertThat(glass.toString(), is("null     10.0     20\n"));
    }

    @Test
    public void whenSecondOppositeItemValueLargerThenFirst() {
        Exchange ex = new Exchange();
        ex.addIssuer("HP");
        ex.addItem("HP", true, true, 10D, 10);
        ex.addItem("HP", true, false, 10D, 15);
        Glass glass = ex.getIssuer(0);
        assertThat(glass.toString(), is("5     10.0     null\n"));
    }

    @Test
    public void whenSecondOppositeItemValueLessThenFirst() {
        Exchange ex = new Exchange();
        ex.addIssuer("HP");
        ex.addItem("HP", true, true, 10D, 15);
        ex.addItem("HP", true, false, 10D, 10);
        Glass glass = ex.getIssuer(0);
        assertThat(glass.toString(), is("null     10.0     5\n"));
    }
}
