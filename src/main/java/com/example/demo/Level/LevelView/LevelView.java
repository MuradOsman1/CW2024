package com.example.demo.Level.LevelView;

import com.example.demo.UserInterface.GameOverImage;
import com.example.demo.UserInterface.HeartDisplay;
import com.example.demo.UserInterface.WinImage;
import javafx.scene.Group;

/**
 * Represents the view for a game level.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	protected final HeartDisplay heartDisplay;

	/**
	 * Constructs a new {@link LevelView} instance.
	 *
	 * @param root           the root group
	 * @param heartsToDisplay the number of hearts to display
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
	}

	/**
	 * Shows the heart display.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}
	/**
	 * Shows the win image.
	 * {@link WinImage#showWinImage()}
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Shows the Game Over image.
	 * {@link GameOverImage#GameOverImage}
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Removes hearts from the display.
	 *{@link HeartDisplay#removeHeart()}
	 * @param heartsRemaining the number of hearts remaining
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Add hearts to the display.
	 *{@link HeartDisplay#addHeart()}
	 */
	public void addHeart() {
        heartDisplay.addHeart(); // Delegate to HeartDisplay
    }


}