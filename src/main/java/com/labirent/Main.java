package com.labirent;

import com.labirent.model.Maze;
import com.labirent.ui.GameView;
import com.labirent.ui.SetupView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Labirent Oyun Motoru");
        
        showSetupScreen();

        primaryStage.show();
    }

    public void showSetupScreen(){
        SetupView setupView = new SetupView(this);
        Scene scene = new Scene(setupView.getView(), 400, 300);
        primaryStage.setScene(scene);
    }

    public void startGame(String playerName, int rows, int cols) {
        Maze maze = new Maze(rows, cols);

        GameView gameView = new GameView(this, maze, playerName);
        Scene scene = new Scene(gameView.getView(), 1000, 700);

        scene.setOnKeyPressed(event -> gameView.handleInput(event.getCode()));

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args){
        launch(args);
    }
}
