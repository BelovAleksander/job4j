package ru.job4j.tracker;

/**
 * Класс-наследник ConsoleInput. Предназначен для перехвата
 * исключений родительского класса.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.06.18
 */
public class ValidateInput extends ConsoleInput {
    /**
     * Переопределенный метод родительского класса.
     * Отправляет параметры методу родителя, перехватывает
     * возможные исключения.
     * @param question Запрос программы.
     * @param range Последовательность допустимых значений.
     * @return результат работы super метода.
     */
    public final int ask(final String question, final int[] range) {
        boolean correct = false;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
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
