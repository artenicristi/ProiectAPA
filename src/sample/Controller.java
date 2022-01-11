package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView Exit;

    @FXML
    private Button Clear;

    @FXML
    private ToggleButton Wall;

    @FXML
    private Button Start;

    @FXML
    private Button Finish;

    @FXML
    private Button Lee;

    @FXML
    private Slider SliderTime;

    @FXML
    private Label Menu;

    @FXML
    private Button BackDFS;

    @FXML
    private Button Liniar;

    @FXML
    private Button Hunter;

    @FXML
    private Button Recursion;

    @FXML
    private Pane pane;

    static int MAX_HEIGHT = 700;
    static int MAX_WIDTH = 1260;
    static int size = 20;

    static int OY = MAX_HEIGHT / size;
    static int OX = MAX_WIDTH / size;
    static boolean existPath = false;

    static Color unvisited = Color.WHITE;
    static Color wall = Color.BLACK;

    Cell[][] grid = new Cell[OX + 5][OY + 5];
    static Point pointToStart = new Point(0, 0, 0, new Circle());
    static Point pointToFinish = new Point(0, 0, 0, new Circle());

    Image finish = new Image("sample/images/FinishPoint.png");
    Image start = new Image("sample/images/StartPoint.png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Exit.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });

        for (int j = 0; j < OY; j ++)
            for (int i = 0; i < OX; i ++) {

                Cell cell = new Cell(i, j, size);
                grid[i][j] = cell;

                pane.getChildren().add(cell.getRectangle());
            }

        pane.setOnMouseClicked(mouseEvent -> setRemoveWall(mouseEvent));
        pane.setOnMouseDragged(mouseEvent -> setRemoveWall(mouseEvent));

    }

    public void dragged(MouseEvent event, Point point) {
        point.setX(event.getX());
        point.setY(event.getY());
        point.draw();
    }

    public void released(MouseEvent event, Point startPoint) {
        int x = (int)event.getX() / size;
        int y = (int)event.getY() / size;

        if (grid[x][y].getRectangle().getFill().equals(wall))
            return;

        startPoint.setX(x * size + size / 2.0);
        startPoint.setY(y * size + size / 2.0);
        startPoint.draw();

        if (existPath) {
            new BFS(SliderTime, pointToStart, pointToFinish, grid, 0).startBFS();
        }
    }

    private void setRemoveWall(MouseEvent mouseEvent) {

        if (Wall.isSelected()) {

            int x = (int) mouseEvent.getX() / size;
            int y = (int) mouseEvent.getY() / size;

            if (x >= 0 && x < OX && y >= 0 && y < OY) {
                Rectangle rectangle = grid[x][y].getRectangle();

                if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                    rectangle.setFill(wall);
                else if (mouseEvent.getButton().equals(MouseButton.SECONDARY))
                    rectangle.setFill(unvisited);
            }
        }
    }

    @FXML
    public void generateHorizontalMaze() {
        new HorizontalMaze(OX, OY, grid, SliderTime).generateMaze();
    }

    @FXML
    public void generateBackDFSMaze() {
        new BackDFSMaze(OX, OY, grid, SliderTime).generateMaze();
    }

    @FXML
    public void generateRecursiveMaze() {
        new RecursiveDivision(OX, OY, grid, SliderTime).generateMaze();
    }

    @FXML
    public void generateHunterMaze() {
        new Hunter(OX, OY, grid, SliderTime).generateMaze();
    }

    @FXML
    public void clearScreen() {
        for (int i = 0; i < OX; i ++)
            for (int j = 0; j < OY; j ++)
                grid[i][j].getRectangle().setFill(unvisited);

        pane.getChildren().remove(pointToStart.getCircle());
        pane.getChildren().remove(pointToFinish.getCircle());

        existPath = false;
    }

    @FXML
    public void generateLee() {
        new BFS(SliderTime, pointToStart, pointToFinish, grid, 5).startBFS();
    }

    @FXML
    public void generateRandomStart() {

        pane.getChildren().remove(pointToStart.getCircle());
        int x, y;

        while (true) {
            x = (int) (Math.random() * OX);
            y = (int) (Math.random() * OY);

            if (grid[x][y].getRectangle().getFill().equals(unvisited))
                break;
        }

        x = x * size + (int)(size / 2.0);
        y = y * size + (int)(size / 2.0);

        Circle circle = new Circle(x, y, size / 2.0);
        circle.setFill(new ImagePattern(start));

        Point startPoint = new Point(x, y, size / 2.0, circle);

        circle.setOnMouseDragged(event -> dragged(event, startPoint));
        circle.setOnMouseReleased(event -> released(event, startPoint));

        pointToStart = startPoint;

        pane.getChildren().add(circle);
        startPoint.draw();
    }

    @FXML
    public void generateRandomFinish() {

        pane.getChildren().remove(pointToFinish.getCircle());

        int x, y;

        while (true) {
            x = (int) (Math.random() * OX);
            y = (int) (Math.random() * OY);

            if (grid[x][y].getRectangle().getFill().equals(unvisited))
                break;
        }

        x = x * size + (int)(size / 2.0);
        y = y * size + (int)(size / 2.0);

        Circle circle = new Circle(x, y, size / 2.0);
        circle.setFill(new ImagePattern(finish));

        Point finishPoint = new Point(x, y, size / 2.0, circle);

        circle.setOnMouseDragged(event -> dragged(event, finishPoint));
        circle.setOnMouseReleased(event -> released(event, finishPoint));

        pointToFinish = finishPoint;

        pane.getChildren().add(circle);
        finishPoint.draw();
    }
}
