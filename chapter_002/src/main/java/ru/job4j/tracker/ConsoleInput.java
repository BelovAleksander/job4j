package ru.job4j.tracker;

import java.util.Scanner;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 10.06.2018
 */
public class ConsoleInput implements Input {
    /**
     * Сканирование введенных данных.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Метод, осуществляющий диалог программы с пользователем.
     * @param question вопрос программы.
     * @return ответ пользователя.
     */
    public final String ask(final String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
}
