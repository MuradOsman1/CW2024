package com.example.demo.Entity.Player;

import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Entities.FighterPlane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashSet;
import java.util.Set;
/**
 * Represents the player's plane in the game.
 * Extends {@link FighterPlane} to provide specific player-controlled behavior.
 */
public class PlayerPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 650;
	private static final double X_LEFT_BOUND = 0;
	private static final double X_RIGHT_BOUND = 950; // Adjust according to screen width
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 30;
	private static final int VELOCITY = 8;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private static int MIN_FRAMES_PER_FIRE = 5;
	private boolean fireRateBoostActive = false;
	private int originalMinFramesPerFire;
	private static final Set<KeyCode> activeKeys = new HashSet<>();
	private int framesSinceLastShot = 0;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a {@link PlayerPlane} with the specified background and initial health.
	 *
	 * @param background the background image for key event registration
	 * @param initialHealth the initial health of the player's plane
	 */
	public PlayerPlane(ImageView background, int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);

		background.setOnKeyPressed(e -> activeKeys.add(e.getCode()));
		background.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));

		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Updates the position of the plane based on user input and screen boundaries.
	 *
	 */
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VELOCITY * verticalVelocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(VELOCITY * horizontalVelocityMultiplier);
			double newPosition = getLayoutX() + getTranslateX();
			if (newPosition < X_LEFT_BOUND || newPosition > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Updates the player's plane, handling input and movement.
	 * {@link PlayerPlane#handleInput()} is called to handle user input.
	 */
	@Override
	public void updateActor() {
		handleInput();
		updatePosition();
	}



	/**
	 * Handles user input to control the plane's movement and actions.
	 */
	private void handleInput() {
		if (activeKeys.contains(KeyCode.UP))
			moveUp();
		if (activeKeys.contains(KeyCode.DOWN))
			moveDown();
		if (activeKeys.contains(KeyCode.LEFT))
			moveLeft();
		if (activeKeys.contains(KeyCode.RIGHT))
			moveRight();

		if ((activeKeys.contains(KeyCode.UP) && activeKeys.contains(KeyCode.DOWN)) || (activeKeys.contains(KeyCode.LEFT) && activeKeys.contains(KeyCode.RIGHT)))
			stop();

		if (!activeKeys.contains(KeyCode.UP) && !activeKeys.contains(KeyCode.DOWN) && !activeKeys.contains(KeyCode.LEFT) && !activeKeys.contains(KeyCode.RIGHT))
			stop();
	}

	/**
	 * Fires a projectile from the player's plane.
	 *
	 * @return a {@link PlayerProjectile} if enough frames have passed since the last shot
	 * otherwise {@code null}
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (framesSinceLastShot >= MIN_FRAMES_PER_FIRE && activeKeys.contains(KeyCode.SPACE)) {
			framesSinceLastShot = 0;
			return new PlayerProjectile(getTranslateX() + 100
					, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		}

		framesSinceLastShot++;
		return null;
	}

	/**
	 * Activates a temporary boost to the plane's fire rate.
	 *
	 * @param durationInMillis the duration of the fire rate boost in milliseconds
	 */
	public void activateFireRateBoost(long durationInMillis) {
		if (!fireRateBoostActive) {
			fireRateBoostActive = true; // Mark the boost as active
			originalMinFramesPerFire = MIN_FRAMES_PER_FIRE; // Store the original fire rate
			MIN_FRAMES_PER_FIRE /= 2; // Double the fire rate (reduce frames between shots)

			// Schedule a task to reset the fire rate after the duration
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					MIN_FRAMES_PER_FIRE = originalMinFramesPerFire; // Restore the original fire rate
					fireRateBoostActive = false; // Mark the boost as inactive
					timer.cancel(); // Stop the timer
				}
			}, durationInMillis);
		}
	}

	/**
	 * Checks if the plane is currently moving vertically.
	 *
	 * @return {@code true} if the plane has a non-zero vertical velocity multiplier, otherwise {@code false}
	 */
	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	/**
	 * Checks if the plane is currently moving horizontally.
	 *
	 * @return {@code true} if the plane has a non-zero horizontal velocity multiplier, otherwise {@code false}
	 */
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	/**
	 * Moves the plane up.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane down.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Moves the plane left.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane right.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops the plane's movement.
	 */
	public void stop() {
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Adds health to the player's plane.
	 * {@link FighterPlane#addHealth()} is called to add health.
	 */
	@Override
	public void addHealth() {
		super.addHealth(); // Calls the addHealth method in FighterPlane
	}


	/**
	 * Gets the total number of kills achieved by the player.
	 *
	 * @return the total kill count
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the player's kill count.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}
}