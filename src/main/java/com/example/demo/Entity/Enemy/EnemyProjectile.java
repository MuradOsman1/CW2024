package com.example.demo.Entity.Enemy;

import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Entities.Projectile;

/**
 * Represents a projectile fired by an enemy plane in the game.
 * This class extends {@link Projectile} and provides functionality
 * for updating the projectile's position.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 25;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME,
				IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile.
	 * {@link ActiveActorDestructible#moveHorizontally(double)}
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor.
	 * {@link ActiveActorDestructible#updatePosition()}
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}


}