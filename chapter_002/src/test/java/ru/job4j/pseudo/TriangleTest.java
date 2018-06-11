package ru.job4j.pseudo;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public class TriangleTest {
    @Test
    public void whenDrawTriangle() {
        Triangle triangle = new Triangle();
        assertThat(triangle.draw(),
                is(
                        new StringBuilder()
                        .append("+\n")
                        .append("++\n")
                        .append("+ +\n")
                        .append("++++")
                        .toString()
                )
        );
    }
}
