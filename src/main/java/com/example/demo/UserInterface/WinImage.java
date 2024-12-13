package com.example.demo.UserInterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the win image displayed at the end of the game.
 * Extends {@link ImageView} for graphical representation.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * Constructs a {@link WinImage} with the specified position and size.
	 *
	 * @param xPosition the x-position of the win image
	 * @param yPosition the y-position of the win image
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Displays the win image, marking the player's victory.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}

}