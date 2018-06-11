package ru.job4j.tracker;

import java.util.Scanner;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 10.06.2018
 */
public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    public String ask(final String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
}
