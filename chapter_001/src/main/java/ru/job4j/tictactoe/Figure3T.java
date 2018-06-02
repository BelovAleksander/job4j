package ru.job4j.tictactoe;

import javafx.scene.shape.Rectangle;
/**
 * Класс Figure3T - отвечает за клетку на поле.
 * Он содержит методы - имеет ли клетка крестик или нолик или пустая.
 */
public class Figure3T extends Rectangle {
    private boolean markX = false;
    private boolean markO = false;

    public Figure3T() {
    }

    public Figure3T(boolean markX) {
        this.markX = markX;
        this.markO = !markX;
    }

    public void take(boolean markX) {
        this.markX = markX;
        this.markO = !markX;
    }

    public boolean hasMarkX() {
        return this.markX;
    }

    public boolean hasMarkO() {
        return this.markO;
    }
}
