package ru.job4j.tracker;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.06.18
 */

public class EmptyDataException extends RuntimeException {
    public EmptyDataException(String msg) {
        super(msg);
    }
}
