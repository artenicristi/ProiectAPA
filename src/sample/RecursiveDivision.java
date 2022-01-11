package sample;

import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class RecursiveDivision extends Maze{

    public RecursiveDivision(int OX, int OY, Cell[][] grid, Slider slider) {
        super(OX, OY, grid, slider);
    }

    @Override
    public void generateMaze() {

        new Thread(() -> {
            for (int i = 0; i < OX; i ++)
                for (int j = 0; j < OY; j ++)
                    grid[i][j].getRectangle().setFill(Controller.unvisited);

            Controller.existPath = false;

            recursiveDivision(0, OX - 1, 0, OY - 1, 0);
        }).start();
    }

    private void recursiveDivision(int leftX, int rightX, int upY, int downY, int direction) {

        if (direction % 2 == 0) {

            if (leftX == rightX)
                return;

            int x;

            while (true) {
                x = (int) (Math.random() * OX);
                if (x % 2 != 0 && x > leftX && x < rightX)
                    break;
            }

            int noWall;

            do {
                noWall = (int) (Math.random() * (downY + 1));
            } while (noWall % 2 == 1 || noWall < upY);

            for (int j = upY; j < noWall; j ++) {
                grid[x][j].getRectangle().setFill(Controller.wall);
                sleep((int)slider.getValue());
            }

            for (int j = noWall + 1; j < downY + 1; j ++) {
                grid[x][j].getRectangle().setFill(Controller.wall);
                sleep((int)slider.getValue());
            }

            direction ++;

            recursiveDivision(leftX, x - 1, upY, downY, direction);
            recursiveDivision(x + 1, rightX, upY, downY, direction);

        } else {

            if (upY == downY)
                return;

            int y;

            while (true) {
                y = (int) (Math.random() * OY);
                if (y % 2 != 0 && y > upY && y < downY)
                    break;
            }

            int noWall;

            do {
                noWall = (int) (Math.random() * (rightX + 1));
            } while (noWall % 2 == 1 || noWall < leftX);

            for (int i = leftX; i < noWall; i ++) {
                grid[i][y].getRectangle().setFill(Controller.wall);
                sleep((int)slider.getValue());
            }

            for (int i = noWall + 1; i < rightX + 1; i ++) {
                grid[i][y].getRectangle().setFill(Controller.wall);
                sleep((int)slider.getValue());
            }

            direction ++;

            recursiveDivision(leftX, rightX, upY, y - 1, direction);
            recursiveDivision(leftX, rightX, y + 1, downY, direction);
        }
    }
}
