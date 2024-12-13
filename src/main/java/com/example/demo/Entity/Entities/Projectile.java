package com.example.demo.Entity.Entities;

import com.example.demo.Entity.ActiveActorDestructible;

/**
 * Represents a projectile in the game.
 */
public abstract class Projectile extends ActiveActorDestructible {


	/**
	 * Constructs a new Projectile instance.
	 *
	 * @param imageName    the name of the image file for the projectile
	 * @param imageHeight  the height of the projectile image
	 * @param initialXPos  the initial X position of the projectile
	 * @param initialYPos  the initial Y position of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Takes damage and destroys the projectile.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 */
	@Override
	public abstract void updatePosition();

}