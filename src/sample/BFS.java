package sample;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class BFS {

    int[][] matrix = new int[Controller.OX + 5][Controller.OY + 5];
    Slider slider;
    Cell[][] grid;

    int x;
    int y;
    int x1;
    int y1;
    int mill;

    int[] dx = new int[]{0, 0, 1, -1};
    int[] dy = new int[]{1, -1, 0, 0};

    Image Up = new Image("/sample/images/Up.png");
    Image Down = new Image("/sample/images/Down.png");
    Image Right = new Image("/sample/images/Right.png");
    Image Left = new Image("/sample/images/Left.png");

    Color visited = Color.rgb(179, 230, 255);
    Color path = Color.rgb(255, 255, 128);
    Color finishPoint = Color.rgb(51, 204, 51);
    Color wall = Color.BLACK;
    Color unvisited = Color.WHITE;


    public BFS(Slider slider, Point startPoint, Point finalPoint, Cell[][] grid, int mill) {
        this.slider = slider;
        this.mill = mill;
        this.grid = grid;

        this.x = (int)(startPoint.getX() / Controller.size);
        this.y = (int)(startPoint.getY() / Controller.size);

        this.x1 = (int)(finalPoint.getX() / Controller.size);
        this.y1 = (int)(finalPoint.getY() / Controller.size);
    }

    public void startBFS() {

        new Thread(() -> {

            for (int l1 = 0; l1 < Controller.OX; l1 ++)
                for (int l2 = 0; l2 < Controller.OY; l2 ++)
                    if (grid[l1][l2].getRectangle().getFill().equals(visited) ||
                            grid[l1][l2].getRectangle().getFill().equals(path) ||
                            grid[l1][l2].getRectangle().getFill().equals(finishPoint) ||
                            grid[l1][l2].getRectangle().getFill().equals(Color.RED))
                        grid[l1][l2].getRectangle().setFill(unvisited);


            for (int i = 0; i < Controller.OX; i ++)
                for (int j = 0; j < Controller.OY; j ++)
                    if (grid[i][j].getRectangle().getFill().equals(wall))
                        matrix[i][j] = -1;

            int[] lee_x = new int[Controller.OX * Controller.OX + 5];
            int[] lee_y = new int[Controller.OX * Controller.OX + 5];
            int[] lee_d = new int[Controller.OX * Controller.OX + 5];

            int a = 0, b = 0;
            lee_x[0] = x;
            lee_y[0] = y;
            lee_d[0] = 1;
            grid[x][y].getRectangle().setFill(visited);
            matrix[x][y] = 1;

            while (a <= b) {

                int currentX = lee_x[a];
                int currentY = lee_y[a];

                for (int k = 0; k < 4; k ++) {
                    int ii = currentX + dx[k];
                    int jj = currentY + dy[k];

                    if (valid(ii, jj)) {
                        b ++;
                        lee_x[b] = ii;
                        lee_y[b] = jj;
                        lee_d[b] = lee_d[a] + 1;
                        matrix[ii][jj] = lee_d[b];
                    }
                }

                a ++;

                for (int i = a; i <= b; i ++){
                    grid[lee_x[i]][lee_y[i]].getRectangle().setFill(visited);
                }

                try {
                    Thread.sleep(mill);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (matrix[x1][y1] != 0) {
                    Controller.existPath = true;
                    break;
                }
            }

            if (matrix[x1][y1] != 0) {
                reconstituire(x1, y1);
                grid[x1][y1].getRectangle().setFill(finishPoint);
            } else grid[x1][y1].getRectangle().setFill(Color.RED);

        }).start();
    }

    private boolean valid(int x, int y) {
        return x >= 0 && x <= Controller.OX - 1 && y >= 0 && y <= Controller.OY - 1 && matrix[x][y] == 0;
    }

    private void reconstituire(int i, int j) {

        if (matrix[i][j] > 1) {
            for (int k = 0; k < 4; k ++) {
                int ii = i + dx[k];
                int jj = j + dy[k];

                if (ii >= 0 && jj >= 0 && ii <= Controller.OX && jj <= Controller.OY && matrix[ii][jj] == matrix[i][j] - 1) {
                    reconstituire(ii, jj);

                    switch (k) {
                        case 0:
                            grid[ii][jj].getRectangle().setFill(new ImagePattern(Up));
                            break;
                        case 1:
                            grid[ii][jj].getRectangle().setFill(new ImagePattern(Down));
                            break;
                        case 2:
                            grid[ii][jj].getRectangle().setFill(new ImagePattern(Left));
                            break;
                        case 3:
                            grid[ii][jj].getRectangle().setFill(new ImagePattern(Right));
                            break;
                    }

                    try {
                        Thread.sleep((int)slider.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    grid[ii][jj].getRectangle().setFill(path);
                    break;
                }
            }
        }
    }
}
