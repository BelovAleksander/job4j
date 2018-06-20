package ru.job4j.tests;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParsingTest {
    @Test
    public void whenStartWithIllegalThenFalse() {
        Parsing test = new Parsing();
        boolean result = test.isValide("}{[]()");
        assertThat(result, is(false));
    }

    @Test
    public void whenSimpleOkThenTrue() {
        Parsing test = new Parsing();
        boolean result = test.isValide("{}[]()");
        assertThat(result, is(true));
    }

    @Test
    public void whenComplexOkThenTrue() {
        Parsing test = new Parsing();
        boolean result = test.isValide("{{[()]}}");
        assertThat(result, is(true));
    }

    @Test
    public void whenDifferentAmountOfOpenAndCloseBracketThenFalse() {
        Parsing test = new Parsing();
        boolean result = test.isValide("{{}()]}");
        assertThat(result, is(false));
    }

    @Test
    public void whenOpenWithoutCloseBracketThenFalse() {
        Parsing test = new Parsing();
        boolean result = test.isValide("{([)]}");
        assertThat(result, is(false));
    }
}
