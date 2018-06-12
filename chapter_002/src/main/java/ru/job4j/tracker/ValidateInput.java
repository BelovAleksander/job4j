package ru.job4j.tracker;

/**
 * Класс-декоратор, расширяющий поведение
 * декорируемого класса.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.06.18
 */
public class ValidateInput implements Input {
    /**
     * переменная для декорируемого класса.
     */
    private Input input;

    /**
     * Конструктор.
     * @param input декорируемый класс
     */
    public ValidateInput(Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }
    /**
     * Переопределенный метод декорируемого класса.
     * Отправляет параметры методу декорируемого класса,
     * перехватывает возможные исключения.
     * @param question Запрос программы.
     * @param range Последовательность допустимых значений.
     * @return результат работы super метода.
     */
    public final int ask(final String question, final int[] range) {
        boolean correct = false;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                correct = true;
            } catch (ItemDoesntExistException idee) {
                System.out.println("Required item doesn't exist.");
            } catch (EmptyDataException ede) {
                System.out.println("Empty Data");
            } catch (MenuOutException moe) {
                System.out.println("Please select key from menu: ");
            } catch (NumberFormatException nfe) {
                 System.out.println("Please, enter validate data: ");
            }
        } while (!correct);
        return value;
    }
}
