package sample;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class HorizontalMaze extends Maze{

    public HorizontalMaze(int OX, int OY, Cell[][] grid, Slider slider) {
        super(OX, OY, grid, slider);
    }

    @Override
    public void generateMaze() {
        new Thread(() -> {

            for (int i = 0; i < OX; i ++)
                for (int j = 0; j < OY; j ++)
                    grid[i][j].getRectangle().setFill(Controller.unvisited);

            Controller.existPath = false;

            for (int i = 0; i < OX; i ++)
                for (int j = 0; j < OY; j ++)
                    if (j == 0)
                        grid[i][j].getRectangle().setFill(Controller.unvisited);
                    else
                        grid[i][j].getRectangle().setFill(Controller.wall);

            ArrayList<Cell> list = new ArrayList<>();

            for (int j = 2; j < OY; j += 2)
                for (int i = 0; i < OX; i += 2) {

                    int choice = (int)(Math.random() * 2 + 1);

                    if (list.isEmpty())
                        choice = 1;

                    if (i == OX - 1) {
                        list.add(grid[i][j]);
                        grid[i][j].getRectangle().setFill(Color.RED);

                        sleep((int)slider.getValue());
                        choice = 2;
                    }

                    switch (choice) {
                        case 1 :
                            list.add(grid[i][j]);
                            grid[i][j].getRectangle().setFill(Color.RED);

                            sleep((int)slider.getValue());
                            break;

                        case 2:

                            int rand = (int)(Math.random() * list.size());

                            Cell cell = list.get(rand);

                            grid[cell.getX()][cell.getY() - 1].getRectangle().setFill(Controller.unvisited);

                            for (int l = list.get(0).getX(); l <= list.get(list.size() - 1).getX(); l ++) {
                                grid[l][cell.getY()].getRectangle().setFill(Controller.unvisited);
                                sleep((int)slider.getValue());
                            }

                            list = new ArrayList<>();

                            if (i < OX - 1)
                                i -= 2;
                    }
                }
        }).start();
    }
}
