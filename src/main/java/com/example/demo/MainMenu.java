package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Modality;

public class MainMenu {

    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/MenuBackground.jpg";

    public void start(Stage primaryStage
    ) {
        // Load the background image
        ImageView background = null;
        try {
            background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE).toExternalForm()));
            background.setFitWidth(1300);
            background.setFitHeight(750);
            background.setPreserveRatio(false);
        } catch (NullPointerException e) {
            System.err.println("Error: Unable to load background image at path: " + BACKGROUND_IMAGE);
            e.printStackTrace();
        }

        // Create buttons
        Button playButton = createButton("Play", event -> {
            try {
                Controller controller = new Controller(primaryStage);
                controller.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to launch the game.");
            }
        });

        Button howToPlayButton = createButton("How to Play", event -> showHowToPlay());

        Button quitButton = createButton("Quit", event -> primaryStage.close());

        // Layout for buttons
        VBox buttonsLayout = new VBox(20);
        buttonsLayout.getChildren().addAll(playButton, howToPlayButton, quitButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        // Create root layout
        StackPane root = new StackPane();
        if (background != null) {
            root.getChildren().add(background);
        }
        root.getChildren().add(buttonsLayout);

        // Set the scene and stage
        Scene scene = new Scene(root, 1300, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setPrefHeight(60);
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, rgba(71,135,255,0.72), rgba(71,135,255,0.72));" +
                        "-fx-text-fill: rgba(0,0,0,0.6);" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-family: 'Monospaced', sans-serif;" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 10, 0, 0, 5);"
        );
        button.setCursor(Cursor.HAND);
        button.setOnAction(eventHandler);
        return button;
    }

    private void showHowToPlay() {
        Stage howToPlayStage = new Stage();
        howToPlayStage.initModality(Modality.APPLICATION_MODAL);
        howToPlayStage.setTitle("How to Play");

        Text instructions = new Text(
                "Welcome to Sky Battle!\n\n"
                        + "Use arrow keys to move.\n"
                        + "Press Space to shoot.\n"
                        + "Avoid enemy attacks and shoot enemies.\n\n"
                        + "Survive as long as you can and defeat the enemies!"
        );
        instructions.setFont(Font.font("Arial", 18));

        Button closeButton = new Button("Close");
        closeButton.setStyle(
                "-fx-background-color: rgba(71,135,255,0.72);" +
                        "-fx-text-fill: rgba(0,0,0,0.6)" +
                        ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-border-radius: 10;"
        );
        closeButton.setOnAction(e -> howToPlayStage.close());

        VBox layout = new VBox(20, instructions, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene howToPlayScene = new Scene(layout, 600, 400);
        howToPlayStage.setScene(howToPlayScene);
        howToPlayStage.showAndWait();
    }
}
