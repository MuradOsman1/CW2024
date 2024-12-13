package com.example.demo.Entity.Entities;

import javafx.scene.image.*;

import java.util.Objects;

/**
 * Represents an active actor in the game.
 * This class extends {@link javafx.scene.image.ImageView} and provides basic functionality
 * for moving the actor horizontally and vertically.
 */
public abstract class ActiveActor extends ImageView {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally
	 * @param horizontalMove the amount to move the actor horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically
	 * @param verticalMove the amount to move the actor vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}