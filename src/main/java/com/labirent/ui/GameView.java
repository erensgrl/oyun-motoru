package com.labirent.ui;

import com.labirent.Main;
import com.labirent.algorithms.*;
import com.labirent.datastructures.AVLTree;
import com.labirent.datastructures.Stack;
import com.labirent.model.*; 
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import com.labirent.io.ScoreJsonReader;
import com.labirent.io.ScoreJsonWriter;
import com.labirent.io.ScoreCsvWriter;


public class GameView {
    private Main mainApp;
    private Maze maze;
    private BorderPane view;
    private Canvas canvas;
    private GraphicsContext gc;

    private String playerName;
    private int playerRow, playerCol;
    private boolean isGameActive = true;
    private int stepCount = 0;
    private long startTime;
    private double cellSize;

    private Timeline gameTimer;

    private Stack<com.labirent.model.Cell> moveHistory;
    private AVLTree<Score> scoreBoard; 

    private Label statusLabel;
    private Label timerLabel;
    private ListView<String> scoreListView;

    public GameView(Main mainApp, Maze maze, String playerName){
        this.mainApp = mainApp;
        this.maze = maze;
        this.playerName = playerName;
        this.moveHistory = new Stack<>();
        this.scoreBoard = new AVLTree<>();

        KruskalMazeGenerator generator = new KruskalMazeGenerator();
        generator.generate(maze);
        
        createView();
        initializeGame();
        draw();
    }

    private void initializeGame(){
        this.playerRow = 0;
        this.playerCol = 0;
        this.isGameActive = true;
        this.stepCount = 0;
        this.startTime = System.currentTimeMillis();
        this.moveHistory = new Stack<>();

        startGameTimer();
    }

    private void startGameTimer(){
        stopGameTimer();

        this.startTime = System.currentTimeMillis();
        timerLabel.setText("Süre: 0s");

        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long now = System.currentTimeMillis();
            long elapsedSeconds = (now - startTime) / 1000;
            timerLabel.setText("Süre: " + elapsedSeconds + "s");
        }));

        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    private void stopGameTimer(){
        if(gameTimer != null){
            gameTimer.stop();
        }
    }

    private void restartLevel(){
        initializeGame();
        statusLabel.setText("Oyun yeniden başlatıldı.");
        statusLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-padding: 10;");
        draw();
    }

    private void createView(){
        view = new BorderPane();
        view.setStyle("-fx-background-color: #2b2b2b;");

        double canvasSize = 600;
        canvas = new Canvas(canvasSize, canvasSize);
        gc =canvas.getGraphicsContext2D();

        this.cellSize = canvasSize / Math.max(maze.rows, maze.cols);
        
        VBox centerBox = new VBox(canvas);
        centerBox.setAlignment(Pos.CENTER);
        view.setCenter(centerBox);

        VBox rightPanel = new VBox(15);
        rightPanel.setPadding(new Insets(15));
        rightPanel.setStyle("-fx-background-color: #34495e;");
        rightPanel.setPrefWidth(220);

        Label controlTitle = new Label("Kontroller");
        controlTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Label playerLabel = new Label("Oyuncu: " + playerName);
        playerLabel.setStyle("-fx-text-fill: #2ecc71;");

        timerLabel = new Label("Süre: 0s");
        timerLabel.setStyle("-fx-text-fill: #ecf0f1;");

        Button restartButton = new Button("Yeniden Başlat");
        restartButton.setOnAction(e -> restartLevel());
        restartButton.setPrefWidth(180);

        Button giveUpButton = new Button("Pes Et / Çözümü Göster");
        giveUpButton.setOnAction(e -> enableSimulationMode(rightPanel));
        giveUpButton.setPrefWidth(180);

        Button backButton = new Button("Ana Menü");
        backButton.setOnAction(e -> {
            stopGameTimer();
            mainApp.showSetupScreen();
        });
        backButton.setPrefWidth(180);
        
        rightPanel.getChildren().addAll(controlTitle, playerLabel, timerLabel, restartButton, giveUpButton, backButton);
        view.setRight(rightPanel);

        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(15));
        leftPanel.setStyle("-fx-background-color: #34495e;");
        leftPanel.setPrefWidth(200);

        Label scoreTitle = new Label ("Skor Tablosu");
        scoreTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        scoreListView = new ListView<>();
        leftPanel.getChildren().addAll(scoreTitle, scoreListView);
        view.setLeft(leftPanel);

        statusLabel = new Label("Oyun başladı. Yön tuşlarını kullanarak hareket edebilirsin.");
        statusLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-padding: 10;");
        view.setBottom(statusLabel);

    }

    public void handleInput(KeyCode code){
        if(!isGameActive) return;

        int newR = playerRow;
        int newC = playerCol;
        int direction = -1;

        if(code == KeyCode.UP || code == KeyCode.W){newR--; direction = 0;}
        else if(code == KeyCode.DOWN || code == KeyCode.S){newR++; direction = 2;}
        else if(code == KeyCode.LEFT || code == KeyCode.A){newC--; direction = 3;}
        else if(code == KeyCode.RIGHT || code == KeyCode.D){newC++; direction = 1;}


        if(maze.isValid(newR, newC)){
            if(direction != -1 && !maze.getCell(playerRow, playerCol).walls[direction]){
                moveHistory.push(maze.getCell(playerRow, playerCol));

                playerRow = newR;
                playerCol = newC;
                stepCount++;
                draw();

                if(playerRow == maze.rows - 1 && playerCol == maze.cols - 1){
                    finishGame(true);
                }
            }
        }
    }
    private void finishGame(boolean win){
        isGameActive = false;
        stopGameTimer();
        double duration = (System.currentTimeMillis() - startTime) / 1000.0;
        
        if(win){
            statusLabel.setText(String.format("Tebrikler! Süre: %.2fs", duration));
            statusLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-padding: 10;");

            Score score = new Score(playerName, duration, stepCount);
            scoreBoard.insert(score);
            updateScoreList(score);
            List<Score> scores = ScoreJsonReader.readScores();
            scores.add(score);

            ScoreJsonWriter.writeScores(scores);
            ScoreCsvWriter.writeScores(scores);
        }
    }

    private void updateScoreList(Score latestScore){
       scoreListView.getItems().clear();
       List<Score> sortedScores = scoreBoard.getSortedList();

       for(Score s : sortedScores){
        scoreListView.getItems().add(s.toString());
       }

       
    }

    private void enableSimulationMode(VBox panel){
        isGameActive = false;
        stopGameTimer();

        statusLabel.setText("Simülasyon Modu: Bir algoritma seçin.");
        panel.getChildren().clear();

        Label lbl = new Label("Algoritma Seç:");
        lbl.setStyle("-fx-text-fill: white;");

        ComboBox<String> algoCombo = new ComboBox<>();
        algoCombo.getItems().addAll("BFS", "DFS", "A*");
        algoCombo.getSelectionModel().select(0);

        Button solveBtn = new Button("Çözümü İzle");
        solveBtn.setOnAction(e -> runAlgorithmAnimation(algoCombo.getSelectionModel().getSelectedIndex()));

        Button backBtn = new Button("Ana Menü");
        backBtn.setOnAction(e -> mainApp.showSetupScreen());

        panel.getChildren().addAll(lbl, algoCombo, solveBtn, backBtn);
    }
    
    private void runAlgorithmAnimation(int algoIndex){
        draw();
        MazeSolver solver;
        if(algoIndex == 0) solver = new BFSSolver();
        else if(algoIndex == 1) solver = new DFSSolver();
        else solver = new AStarSolver();

        com.labirent.model.Cell start = maze.getCell(0, 0);
        com.labirent.model.Cell end = maze.getCell(maze.rows - 1, maze.cols - 1);

        PathFindingResult result = solver.solve(maze, start, end);

        if(!result.success){
            statusLabel.setText("Çözüm bulunamadı.");
            return;
        }

        statusLabel.setText("Algoritma çalışıyor...");

        Timeline timeline = new Timeline();
        int durationPerStep = 20;

        for(int i = 0; i < result.visitedOrder.size(); i++){
            com.labirent.model.Cell c = result.visitedOrder.get(i);
            KeyFrame kf = new KeyFrame(Duration.millis(i * durationPerStep), e -> {
                drawCell(c.row, c.col, Color.rgb(52, 152, 219, 0.5));
            });
            timeline.getKeyFrames().add(kf);
        }

        double startTimePath = result.visitedOrder.size() * durationPerStep;
        for(int i = 0; i < result.path.size(); i++){
            com.labirent.model.Cell c = result.path.get(i);
            KeyFrame kf = new KeyFrame(Duration.millis(startTimePath + (i * durationPerStep)), e -> {
                drawCell(c.row, c.col, Color.LIMEGREEN);
            });
            timeline.getKeyFrames().add(kf);
        }

        timeline.play();
    }

    private void draw(){
        gc.setFill(Color.web("#2b2b2b"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.web("#ecf0f1"));
        gc.setLineWidth(2);

        for(int r = 0; r < maze.rows; r++){
            for(int c = 0; c < maze.cols; c++){
                double x = c * cellSize;
                double y = r * cellSize;
                com.labirent.model.Cell cell = maze.getCell(r, c);

                if(cell.walls[0]) gc.strokeLine(x, y, x + cellSize, y);
                if(cell.walls[1]) gc.strokeLine(x + cellSize, y, x + cellSize, y + cellSize);
                if(cell.walls[2]) gc.strokeLine(x, y + cellSize, x + cellSize, y + cellSize);
                if(cell.walls[3]) gc.strokeLine(x, y, x, y + cellSize);
            }
        }

        drawCell(0, 0, Color.BLUE);
        drawCell(maze.rows - 1, maze.cols - 1, Color.RED);

        if(isGameActive){
            double pX = playerCol * cellSize + 5;
            double pY = playerRow * cellSize + 5;

            gc.setFill(Color.YELLOW);
            gc.fillOval(pX, pY, cellSize - 10, cellSize - 10);

        }
    }

    private void drawCell(int r, int c, Color color){
        double x = c * cellSize;
        double y = r * cellSize;

        gc.setFill(color);
        gc.fillRect(x + 2, y + 2, cellSize - 4, cellSize - 4);
    }

    public Parent getView(){
        return view;
    }
}