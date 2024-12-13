package com.example.demo.UserInterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents a shield image in the game.
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 200;

	/**
	 * Constructs a new ShieldImage instance.
	 *
	 * @param xPosition the X position of the shield image
	 * @param yPosition the Y position of the shield image
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Shows the shield image.
	 */
	public void showShield() {
		this.setVisible(true);
		toFront();
	}

	/**
	 * Hides the shield image.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

}