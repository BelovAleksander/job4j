package ru.job4j.pseudo;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public class PaintTest {
    // поле содержит дефолтный вывод в консоль.
    private PrintStream stdout = System.out;
    // буфер для результата
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutput() {
        System.setOut(stdout);
        System.out.println("execute after method");
    }

    @Test
    public void whenDrawSquare() {
        new Paint().draw(new Square());
        // проверяем результат вычисления
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("++++\n")
                                .append("+  +\n")
                                .append("+  +\n")
                                .append("++++")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenDrawTriangle() {
        new Paint().draw(new Triangle());
        assertThat(new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("+\n")
                                .append("++\n")
                                .append("+ +\n")
                                .append("++++")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }
}
