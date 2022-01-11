package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {

    private int x;
    private int y;
    private Rectangle rectangle;

    public Cell(int x, int y, int size) {
        this.x = x;
        this.y = y;

        this.rectangle = new Rectangle(x * size, y * size, size, size);
        this.rectangle.setFill(Color.WHITE);
        this.rectangle.setStroke(Color.BLACK);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
