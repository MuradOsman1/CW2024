package com.example.demo.Entity.Player;

import com.example.demo.Entity.Entities.Projectile;

/**
 * Represents a projectile fired by the player.
 * Extends {@link Projectile} to define specific behavior for user projectiles.
 */
public class PlayerProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 5;
	private static final int HORIZONTAL_VELOCITY = 20;

	/**
	 * Constructs a {@link PlayerProjectile} with the specified initial position.
	 *
	 * @param initialXPos the initial x-position of the projectile
	 * @param initialYPos the initial y-position of the projectile
	 */
	public PlayerProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile, moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	/**
	 * Updates the projectile's behavior on each frame.
	 * calls the {@link PlayerProjectile#updatePosition()} method to move the projectile horizontally.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}