package ru.job4j.list;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SortUserTest {
    PrintStream stdOut = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutPut() {
        System.out.println("execute before method...");
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutPut() {
        System.setOut(stdOut);
        System.out.println("execute after method...");
    }

    @Test
    public void whenThreeUsers43And9And25Then9And25And43() {
        User first = new User("alex", 43);
        User second = new User("vlad", 9);
        User third = new User("max", 25);
        List<User> list = new ArrayList<>(Arrays.asList(first, second, third));
        Set<User> result = new SortUser().sort(list);
        for (User user : result) {
            System.out.print(user.getAge() + " ");
        }
        assertThat(this.out.toString(), is(
                new StringBuilder()
                .append("9 ")
                .append("25 ")
                .append("43 ")
                .toString()
        ));
    }
}
