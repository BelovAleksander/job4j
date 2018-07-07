package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void run() {
        double x = 1D;
        double y = 0.5;
        while (true) {
            this.rect.setX(this.rect.getX() + x);
            this.rect.setY(this.rect.getY() + y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.rect.getX() == 300) {
                x = -1;
            } else if (this.rect.getX() == 0) {
                x = 1;
            }
            if (this.rect.getY() == 300) {
                y = -0.5;
            } else if (this.rect.getY() == 0) {
                y = 0.5;
            }
        }
    }
}
