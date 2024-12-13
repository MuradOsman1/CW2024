package com.example.demo.Entity;


import com.example.demo.Entity.Entities.ActiveActor;
import com.example.demo.Entity.Entities.Destructible;

/**
 * Represents a destructible active actor in the game.
 * This class extends {@link ActiveActor} and implements {@link Destructible},
 * providing additional functionality for handling destruction and damage.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the actor
	 */
	public abstract void updateActor();

	/**
	 * Takes damage from the actor
	 */
	@Override
	public abstract void takeDamage();


	/**
	 * Destroys the actor
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destruction status of the actor.
	 *
	 * @param isDestroyed the new destruction status.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if this destructible actor has been destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

}