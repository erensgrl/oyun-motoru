package com.labirent;

import com.labirent.model.Maze;
import com.labirent.model.Cell;
import com.labirent.model.CellType;
import com.labirent.ui.MazeUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Maze maze = new Maze(10, 10);
        MazeUI mazeUI = new MazeUI();

        GridPane mazeView = mazeUI.createMazeView(maze);

        Scene scene = new Scene(mazeView);
        stage.setTitle("Labirent Oyunu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String[] args) {launch(args);
    }
}