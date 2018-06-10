package ru.job4j.tracker;

import java.util.Scanner;

public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);

    public String ask(final String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
}
