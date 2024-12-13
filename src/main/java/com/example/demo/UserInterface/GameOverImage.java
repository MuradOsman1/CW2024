package com.example.demo.UserInterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a GameOverImage and positions it at the specified coordinates.
	 * The image associated with this object represents a "Game Over" screen.
	 *
	 * @param xPosition the x-coordinate for the position of the image
	 * @param yPosition the y-coordinate for the position of the image
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}