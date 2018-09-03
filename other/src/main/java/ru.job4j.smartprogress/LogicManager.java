package ru.job4j.smartprogress;

public class LogicManager {
    private final int magicNumber;

    public LogicManager(int number) {
        this.magicNumber = number;
    }

    public String guess(int number) {
        String result;
        if (number == magicNumber) {
            result = "profit!";
        } else if (magicNumber > number) {
            result = "larger";
        } else {
            result = "less";
        }
        return result;
    }

}
