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
    public int ask(final String question, int[] range) {
        int key = Integer.parseInt(this.ask(question));
        boolean correct = false;
        for (int value : range) {
            if (key == value) {
                correct = true;
                break;
            }
        }
        if (!correct) {
            throw new MenuOutException("Out of menu range");
        }
        return key;
    }
}
