package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Class CalculatorTest
* @author Alexander Belov (whiterabbit.nsk@gmail.com)
* @version 0.1
* since 24.05.2018
*/

public class CalculatorTest {
	@Test
	public void whenAddOnePlusOneThenTwo() {
		final Calculator calc = new Calculator();
		calc.add(1D, 1D);
		final double result = calc.getResult();
		double expected = 2D;
		assertThat(result, is(expected));
	}
	@Test
	public void whenSubtractOneMinusOneThenZero() {
		final Calculator calc = new Calculator();
		calc.subtract(1D, 1D);
		final double result = calc.getResult();
		double expected = 0D;
		assertThat(result, is(expected));
	}
	@Test
	public void whenMultipleTwoAndTwoThenFour() {
		final Calculator calc = new Calculator();
		calc.multiple(2D, 2D);
		final double result = calc.getResult();
		double expected = 4D;
		assertThat(result, is(expected));
	}
	@Test
	public void whenDivTwoAndTwoThenOne() {
		final Calculator calc = new Calculator();
		calc.div(2D, 2D);
		final double result = calc.getResult();
		double expected = 1D;
		assertThat(result, is(expected));
	}
		
}


		
	