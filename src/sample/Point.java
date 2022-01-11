package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point {

    private double x;
    private double y;
    private double radius;
    private Circle circle;

    public Point(double x, double y, double radius, Circle circle) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.circle = circle;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setColor(Color color) {
        this.circle.setFill(color);
    }

    public void draw() {
        this.circle.setCenterX(x);
        this.circle.setCenterY(y);
    }
}
