package ru.job4j.tictactoe;

/**
 * Класс Logic3T отвечает за проверку логики.
 */
public class Logic3T {
    // поле
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    public boolean isWinnerX() {
        Figure3T cross = new Figure3T(true);
        return hasCombination(cross);
    }

    public boolean isWinnerO() {
        Figure3T zero = new Figure3T(false);
        return hasCombination(zero);

    }

    public boolean hasGap() {
        boolean result = false;
        for (Figure3T[] row : table) {
            for (Figure3T element : row) {
                if ((!element.hasMarkO()) && (!element.hasMarkX())) {
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean hasCombination(Figure3T figure) {
        boolean result1 = true;
        boolean result2 = true;
        boolean result3 = false;
        boolean result4 = false;
        for (int i = 0; i != table.length; i++) {
            // проверка диагоналей
            if (table[i][i] != figure) {
                result1 = false;
            } else if (table[i][table.length - 1 - i] != figure) {
                result2 = false;
            }
            for (int j = 0; j != table[i].length; j++) {
                // проверка горизонталей
                if (table[i][j] != figure) {
                    break;
                } else if (table[i].length - j == 1) {
                    result3 = true;
                }
            }
            for (int k = 0; k != table[i].length; k++) {
                // проверка вертикалей
                if (table[k][i] != figure) {
                    break;
                } else if (table[i].length - k == 1) {
                    result4 = true;
                }
            }
        }
        return result1 || result2 || result3 || result4;
    }
}
