package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* author Alexander Belov (whiterabbit.nsk@gmail.com)
* @version $Id$
* @since 0.1
*/
public class MaxTest {
	@Test
	public void whenFirstLessSecond() {
		Max maxim = new Max();
		int result = maxim.max(1,2);
		assertThat(result, is(2));
	}

	@Test
	public void whenFirstMoreSecondAndLessThird() {
		Max maxim = new Max();
		int result = maxim.max(3,2,4);
		assertThat(result, is(4));
	}
}
