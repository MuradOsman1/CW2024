package com.example.demo;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashSet;
import java.util.Set;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 650;
	private static final double X_LEFT_BOUND = 0;
	private static final double X_RIGHT_BOUND = 950; // Adjust according to screen width
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 30;
	private static final int VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 100;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private static int MIN_FRAMES_PER_FIRE = 5;
	private boolean fireRateBoostActive = false;
	private int originalMinFramesPerFire;
	private static final Set<KeyCode> activeKeys = new HashSet<>();
	private int framesSinceLastShot = 0;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	public UserPlane(ImageView background, int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);

		background.setOnKeyPressed(e -> activeKeys.add(e.getCode()));
		background.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));

		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

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

	@Override
	public void updateActor() {
		handleInput();
		updatePosition();
	}




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

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (framesSinceLastShot >= MIN_FRAMES_PER_FIRE && activeKeys.contains(KeyCode.SPACE)) {
			framesSinceLastShot = 0;
			return new UserProjectile(getTranslateX() + 100
					, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		}

		framesSinceLastShot++;
		return null;
	}

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

	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stop() {
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	@Override
	public void addHealth() {
		super.addHealth(); // Calls the addHealth method in FighterPlane
	}


	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
}