package ru.job4j.smartprogress;

import java.util.Random;

public class Bot {
    private int max;
    private int min;
    private Random rand = new Random();

    public Bot(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNumber() {
        System.out.println("Computer guesses: ");
        int number = this.rand.nextInt((max - min) + 1) + min;
        System.out.println(number);
        return number;
    }
}
