package com.labirent.ui;

import com.labirent.Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SetupView {
    private Main mainApp;
    private VBox view;

    public SetupView(Main mainApp){
        this.mainApp = mainApp;
        createView();
    }

    private void createView(){
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #2b2b2b;");
     
        Label titleLabel = new Label("Labirent Oyun Motoru");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Oyuncu Adı:");
        nameLabel.setStyle("-fx-text-fill: #ecf0f1;");
        TextField nameField = new TextField();
        nameField.setMaxWidth(200);

        Label sizeLabel = new Label("Labirent boyutu (NxN):");
        sizeLabel.setStyle("-fx-text-fill: #ecf0f1;");

        Spinner<Integer> sizeSpinner = new Spinner<>(10, 50, 20);
        sizeSpinner.setEditable(true);

        Button startButton = new Button("LABİRENT OLUŞTUR");
        startButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px;");
        startButton.setPrefWidth(200);

        startButton.setOnAction(e -> {
            String playerName = nameField.getText();
            int size = sizeSpinner.getValue();


            mainApp.startGame(playerName, size, size);
        });

        view.getChildren().addAll(titleLabel, nameLabel, nameField, sizeLabel, sizeSpinner, startButton);
    }

    public Parent getView(){
        return view;
    }
}
