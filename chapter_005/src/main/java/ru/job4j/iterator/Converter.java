package ru.job4j.iterator;

import java.util.Iterator;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 29.06.18
 *
 * Класс реализует проход по вложенным итераторам.
 */

public class Converter {
    private boolean newItStart = true;
    private Iterator<Integer> innerIt;

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> innerIt = it.next();

            @Override
            public boolean hasNext() {
                if (!innerIt.hasNext() && it.hasNext()) {
                    innerIt = it.next();
                }
                return innerIt.hasNext();
            }

            @Override
            public Integer next() {
                if (!innerIt.hasNext() && it.hasNext()) {
                    innerIt = it.next();
                }
                return innerIt.next();
            }
        };
    }
}
