package ru.job4j.concurrency;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gamil.com)
 * @since 16.07.18
 */

public class ParallelSearchTest {

    @Test
    public void whenCreateStringThenSearchFindIt() {
        String toFind = "OneOfAKindString";
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("java");
        ParallelSearch ps = new ParallelSearch("c://projects", toFind, extensions);
        ps.init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = ps.result();
//        assertThat(result.contains(
//                "c:\\projects\\job4j\\chapter_006\\src\\test\\java\\ru\\job4j\\concurrency\\ParallelSearchTest.java"),
//                is(true));
    }
}