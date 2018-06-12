package ru.job4j.tracker;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public interface Input {
    /**
     * Метод, осуществляющий диалог программы с пользователем.
     * @param question вопрос программы.
     * @return ответ пользователя.
     */
    String ask(String question);
    int ask(String question, int[] range);
}
