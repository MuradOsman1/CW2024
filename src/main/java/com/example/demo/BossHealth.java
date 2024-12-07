package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BossHealth {

    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_SIZE = 30;
    private static final double SPACING = 5;

    private final HBox container;
    private final Label healthCounter;
    private int health;

    public BossHealth(double xPosition, double yPosition, int initialHealth) {
        this.health = initialHealth;
        this.container = new HBox(SPACING);

        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        // Add heart image
        ImageView heartImage = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
        heartImage.setFitHeight(HEART_SIZE);
        heartImage.setFitWidth(HEART_SIZE);
        container.getChildren().add(heartImage);

        // Add health counter label
        this.healthCounter = new Label("x" + health);
        healthCounter.setStyle("-fx-font-size: 18px; -fx-text-fill: #ad9f9f;");
        container.getChildren().add(healthCounter);
    }

    public void updateHealth(int newHealth) {

        this.health = newHealth;
        healthCounter.setText("x " + health);
    }

    public HBox getContainer() {
        return container;
    }
}