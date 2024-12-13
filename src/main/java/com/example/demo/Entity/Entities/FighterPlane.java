package com.example.demo.Entity.Entities;

import com.example.demo.Entity.ActiveActorDestructible;

/**
 * Represents a fighter plane in the game.
 * This class extends {@link ActiveActorDestructible} and provides additional functionality
 * specific to fighter planes, such as firing projectiles and managing health.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * Constructs a new {@link FighterPlane} instance.
	 *
	 * @param imageName    the name of the image file for the fighter plane
	 * @param imageHeight  the height of the fighter plane image
	 * @param initialXPos  the initial X position of the fighter plane
	 * @param initialYPos  the initial Y position of the fighter plane
	 * @param health       the initial health of the fighter plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}


	/**
	 * Fires a projectile from the fighter plane.
	 *
	 * @return the fired {@link ActiveActorDestructible} projectile
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Takes damage and reduces the fighter plane's health.
	 * If health reaches zero, the fighter plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Adds health to the fighter plane. Used for the Health Power-Up.
	 */
	public void addHealth() {
		health++;
	}

	/**
	 * Gets the X position for the projectile to be fired.
	 *
	 * @param xPositionOffset the offset to be added to the current X position
	 * @return the calculated X position for the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Gets the Y position for the projectile to be fired.
	 *
	 * @param yPositionOffset the offset to be added to the current Y position
	 * @return the calculated Y position for the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the fighter plane's health has reached zero.
	 *
	 * @return true if health is zero, false otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return the current health
	 */
	public int getHealth() {
		return health;
	}

}