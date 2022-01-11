package sample;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Stack;

public class BackDFSMaze extends Maze {

    protected int[][] wall = new int[OX][OY];

    public BackDFSMaze(int OX, int OY, Cell[][] grid, Slider slider) {
        super(OX, OY, grid, slider);
    }

    protected ArrayList<Cell> getNeighbours(Cell current) {

        ArrayList<Cell> list = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();

        if (isValid(x - 2, y) && isVisited(x - 2, y))
            list.add(grid[x - 2][y]);

        if (isValid(x + 2, y) && isVisited(x + 2, y))
            list.add(grid[x + 2][y]);

        if (isValid(x, y - 2) && isVisited(x, y - 2))
            list.add(grid[x][y - 2]);

        if (isValid(x, y + 2) && isVisited(x, y + 2))
            list.add(grid[x][y + 2]);

        return list;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < OX && y >= 0 && y < OY;
    }

    private boolean isVisited(int x, int y) {

        boolean left = false, right = false, up = false, down = false;

        if (y == 0)
            up = true;
        if (x == 0)
            left = true;
        if (y == OY - 1)
            down = true;
        if (x == OX - 1)
            right = true;

        return wall[x][y] == 1 && (up ? true : wall[x][y - 1] == 1)
                && (down ? true : wall[x][y + 1] == 1) && (left ? true : wall[x - 1][y] == 1)
                && (right ? true : wall[x + 1][y] == 1);
    }

    @Override
    public void generateMaze() {

        new Thread(() -> {

            for (int i = 0; i < OX; i ++)
                for (int j = 0; j < OY; j ++)
                    grid[i][j].getRectangle().setFill(Controller.unvisited);

            Controller.existPath = false;

            for (int j = 0; j < OY; j ++)
                for (int i = 0; i < OX; i ++){
                    grid[i][j].getRectangle().setFill(Controller.wall);
                    wall[i][j] = 1;
                }

            Stack<Cell> stack = new Stack<>();

            Cell current = grid[0][0];
            grid[0][0].getRectangle().setFill(Controller.unvisited);
            wall[0][0] = 0;

            stack.push(current);

            while (!stack.isEmpty()) {

                ArrayList<Cell> neighbours = getNeighbours(current);

                if (neighbours.size() > 0) {

                    Cell nextCell = neighbours.get((int)(Math.random() * neighbours.size()));
                    wall[nextCell.getX()][nextCell.getY()] = 0;
                    nextCell.getRectangle().setFill(Controller.unvisited);

                    if (current.getX() - nextCell.getX() < 0) {
                        grid[current.getX() + 1][current.getY()].getRectangle().setFill(Controller.unvisited);
                        wall[current.getX() + 1][current.getY()] = 0;
                    }
                    else if (current.getX() - nextCell.getX() > 0) {
                        grid[current.getX() - 1][current.getY()].getRectangle().setFill(Controller.unvisited);
                        wall[current.getX() - 1][current.getY()] = 0;
                    } else

                    if (current.getY() - nextCell.getY() < 0) {
                        grid[current.getX()][current.getY() + 1].getRectangle().setFill(Controller.unvisited);
                        wall[current.getX()][current.getY() + 1] = 0;
                    }
                    else if (current.getY() - nextCell.getY() > 0) {
                        grid[current.getX()][current.getY() - 1].getRectangle().setFill(Controller.unvisited);
                        wall[current.getX()][current.getY() - 1] = 0;
                    }

                    nextCell.getRectangle().setFill(Controller.unvisited);
                    stack.push(nextCell);
                    current = nextCell;

                } else {
                    current = stack.pop();
                    current.getRectangle().setFill(Controller.unvisited);
                }

                sleep((int)slider.getValue());
            }
        }).start();
    }
}
