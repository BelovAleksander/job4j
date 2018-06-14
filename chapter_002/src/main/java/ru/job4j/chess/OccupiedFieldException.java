package ru.job4j.chess;

public class OccupiedFieldException extends RuntimeException {
    public OccupiedFieldException(String msg) {
        super(msg);
    }
}
