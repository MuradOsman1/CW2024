package com.example.demo.Entity.Boss;

import com.example.demo.Entity.Entities.Projectile;

/**
 * Represents a projectile fired by the boss in the game.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a new {@link BossProjectile} instance.
	 *
	 * @param initialYPos the initial Y position of the projectile
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}
	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	/**
	 * Updates the state of the projectile, and calls the {@link #updatePosition()} method.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
