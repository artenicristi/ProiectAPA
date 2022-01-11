package sample;

import javafx.scene.control.Slider;

public abstract class Maze {

    protected int OX;
    protected int OY;
    protected Cell[][] grid;
    protected Slider slider;

    public Maze(int OX, int OY, Cell[][] grid, Slider slider) {
        this.OX = OX;
        this.OY = OY;
        this.grid = grid;
        this.slider = slider;
    }

    protected void sleep(int mill) {

        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void generateMaze();
}
