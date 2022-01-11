package sample;

import javafx.scene.control.Slider;

import java.util.ArrayList;

public class Hunter extends BackDFSMaze {

    public Hunter(int OX, int OY, Cell[][] grid, Slider slider) {
        super(OX, OY, grid, slider);
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

            int x, y;

            do {
                x = (int) (Math.random() * OX);
            } while (x % 2 == 1);

            do {
                y = (int) (Math.random() * OY);
            } while (y % 2 == 1);

            Cell current = grid[x][y];
            current.getRectangle().setFill(Controller.unvisited);
            wall[x][y] = 0;

            boolean go = true;

            while (go) {

                go = false;

                DFS(current);

                for (int j = 0; j < OY; j += 2) {

                    if (go)
                        break;

                    for (int i = 0; i < OX; i += 2){
                        if (wall[i][j] == 0 && getNeighbours(grid[i][j]).size() > 0) {
                            go = true;
                            current = grid[i][j];

                            sleep((int)slider.getValue());
                            grid[i][j].getRectangle().setFill(Controller.unvisited);

                            break;
                        }
                    }
                }
            }
        }).start();
    }

    private void DFS(Cell current) {

        while (true) {

            ArrayList<Cell> neighbours = getNeighbours(current);

            if (neighbours.size() > 0) {

                sleep((int)slider.getValue());

                Cell nextCell = neighbours.get((int)(Math.random() * neighbours.size()));
                wall[nextCell.getX()][nextCell.getY()] = 0;

                if (current.getX() - nextCell.getX() < 0) {
                    sleep((int)slider.getValue());
                    grid[current.getX() + 1][current.getY()].getRectangle().setFill(Controller.unvisited);
                    wall[current.getX() + 1][current.getY()] = 0;
                } else if (current.getX() - nextCell.getX() > 0) {
                    sleep((int)slider.getValue());
                    grid[current.getX() - 1][current.getY()].getRectangle().setFill(Controller.unvisited);
                    wall[current.getX() - 1][current.getY()] = 0;
                } else if (current.getY() - nextCell.getY() < 0) {
                    sleep((int)slider.getValue());
                    grid[current.getX()][current.getY() + 1].getRectangle().setFill(Controller.unvisited);
                    wall[current.getX()][current.getY() + 1] = 0;
                } else if (current.getY() - nextCell.getY() > 0) {
                    sleep((int)slider.getValue());
                    grid[current.getX()][current.getY() - 1].getRectangle().setFill(Controller.unvisited);
                    wall[current.getX()][current.getY() - 1] = 0;
                }
                nextCell.getRectangle().setFill(Controller.unvisited);

                current = nextCell;
                continue;
            } else break;
        }
    }
}
