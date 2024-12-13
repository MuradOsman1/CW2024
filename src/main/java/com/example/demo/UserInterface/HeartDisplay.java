package com.example.demo.UserInterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The HeartDisplay class is responsible for displaying a collection of heart-shaped images
 * in a horizontal layout, represented by an HBox container. This component can be used
 * in user interfaces to visually indicate a countable property such as lives or health.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for displaying heart-shaped images.
	 * Sets the container to an HBox layout and positions it at the specified
	 * X and Y coordinates provided by {@param containerXPosition} and {@param containerYPosition}.
	 *
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the collection of heart-shaped images for display.
	 * This method creates a specific number of ImageView instances representing hearts,
	 * based on the defined {@code numberOfHeartsToDisplay}.
	 * Each ImageView is set to display a heart image, and the container is updated
	 * {@link HBox}
	 * {@link ImageView}to include these heart-shaped images.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes the first heart from the heart display if any hearts are present.
	 * This method checks if the container holding heart-shaped images is not empty,
	 * and removes the heart located at the first index of the container.
	 * {@param INDEX_OF_FIRST_ITEM} is a constant representing the index of the first item in the container.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}
	/**
	 * Adds a heart to the heart display.
	 * This method creates a new ImageView instance representing a heart, sets its size and
	 * {@param HEART_HEIGHT} is a constant representing the height of the heart image.
	 * {@link ImageView} is a JavaFX class used to display images in a graphical user interface.
	 */
	public void addHeart() {
		ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * Retrieves the container for displaying heart-shaped images.
	 * The container is an HBox layout that holds the heart images.
	 *
	 * @return the HBox container used for displaying heart-shaped images.
	 */
	public HBox getContainer() {
		return container;
	}

}