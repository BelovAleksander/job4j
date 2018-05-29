
package ru.job4j.array;

public class MatrixCheck {
    public boolean mono(boolean[][] data) {
        boolean result = true;
        for (int i = 0; i < data.length; i++) {
            if ((data[i][i] != data[0][0]) || (data[i][data.length - i - 1] != data[0][0])) {
                result = false;
                break;
            }
        }
        return result;
    }
}
