package com.example.demo.Entity.Boss;

import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Entities.FighterPlane;

import java.util.*;
/**
 * Represents the boss enemy in the game.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1040;
	private static final double INITIAL_Y_POSITION = 300;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0.0;
	private static final double BOSS_SHIELD_PROBABILITY = .70;
	private static final int IMAGE_HEIGHT = 80;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 20;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int MIN_FRAMES_PER_FIRE = 60;
	private static final int MAX_FRAMES_PER_FIRE = 150;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int FRAMES_PER_SHIELD_CHANCE = 300;
	private static final int MAX_FRAMES_WITH_SHIELD = 90;
	private int framesBeforeNextShot = 0;
	private int framesBeforeNextShieldChance = 0;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int shieldActivatedFrames;

	/**
	 * Constructs a new {@link Boss} instance.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		shieldActivatedFrames = 0;
		isShielded = false;
		initializeMovePattern();
	}
	/**
	 * Checks if the boss is shielded.
	 * return true if the boss is shielded, false otherwise
	 */
	public boolean getIsShielded() {
		return isShielded;
	}

	/**
	 * Updates the position of the boss by moving it vertically.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the state of the boss, calls the {@link #updatePosition()} and {@link #updateShield()} methods.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the boss.
	 *
	 * @return a new {@link BossProjectile} if the boss fires, null otherwise
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Takes damage and reduces the boss's health.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the movement pattern for the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}

		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss.
	 */
	private void updateShield() {
		if (isShielded)
			shieldActivatedFrames++;
		else if (shieldActivated())
			activateShield();

		if (shieldExhausted())
			deactivateShield();
	}

	/**
	 * Gets the next move for the boss.
	 *
	 * @return the next move value
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Checks if the boss fires in the current frame.
	 *
	 * @return true if the boss fires, false otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		if (framesBeforeNextShot <= 0) {
			framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
			return true;
		}

		framesBeforeNextShot--;

		return false;
	}

	/**
	 * Gets the initial position for the projectile.
	 *
	 * @return the initial Y position for the projectile
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Checks if the shield is activated.
	 *
	 * @return true if the shield is activated, false otherwise
	 */
	private boolean shieldActivated() {
		if (framesBeforeNextShieldChance <= 0) {
			framesBeforeNextShieldChance = FRAMES_PER_SHIELD_CHANCE;
			return Math.random() < BOSS_SHIELD_PROBABILITY;
		}


		framesBeforeNextShieldChance--;

		return false;
	}

	/**
	 * Checks if the shield is exhausted.
	 *
	 * @return true if the shield is exhausted, false otherwise
	 */
	private boolean shieldExhausted() {
		return shieldActivatedFrames >= MAX_FRAMES_WITH_SHIELD;
	}


	/**
	 * Activates the shield for the boss.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the shield for the boss.
	 */
	private void deactivateShield() {
		isShielded = false;
		shieldActivatedFrames = 0;
	}

}