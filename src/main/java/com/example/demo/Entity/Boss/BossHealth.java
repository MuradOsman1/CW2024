package com.example.demo.Entity.Boss;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
/**
 * Represents the health display for the boss in the game.
 */
public class BossHealth {

    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_SIZE = 30;
    private static final double SPACING = 5;

    private final HBox container;
    private final Label healthCounter;
    private int health;

    /**
     * Constructs a new {@link BossHealth} instance.
     *
     * @param xPosition     the X position of the health display
     * @param yPosition     the Y position of the health display
     * @param initialHealth the initial health value
     */
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

    /**
     * Updates the health value displayed.
     *
     * @param newHealth the new health value
     */
    public void updateHealth(int newHealth) {

        this.health = newHealth;
        healthCounter.setText("x " + health);
    }

    /**
     * Gets the container for the health display.
     *
     * @return the {@link HBox} container
     */
    public HBox getContainer() {
        return container;
    }
}