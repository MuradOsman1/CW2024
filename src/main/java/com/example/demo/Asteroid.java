package com.example.demo;

import com.example.demo.Entity.ActiveActorDestructible;

/**
 * Represents an asteroid in the game.
 * This class extends {@link ActiveActorDestructible} and provides functionality
 * for updating the asteroid's position and handling its destruction.
 */
public class Asteroid extends ActiveActorDestructible {

    private static final int DEFAULT_SIZE = 120;  // Default asteroid size
    private final double xVelocity;
    private final double yVelocity;

    public Asteroid(String imageName, double initialXPos, double initialYPos, double angle, double speed) {
        super(imageName, DEFAULT_SIZE, initialXPos, initialYPos);

        // Calculate velocities based on angle and speed
        double radians = Math.toRadians(angle);
        this.xVelocity = speed * Math.cos(radians);
        this.yVelocity = speed * Math.sin(radians);

        // Set asteroid size
        setFitHeight(DEFAULT_SIZE);
        setPreserveRatio(true);
    }

    /**
     * Updates the asteroid's position based on its velocities.
     *
     */
    @Override
    public void updatePosition() {
        moveHorizontally(xVelocity); // Diagonal horizontal movement
        moveVertically(yVelocity);  // Vertical movement
    }

    /**
     * Updates the state of the asteroid actor. This method first updates the asteroid's
     * position by moving it based on its predefined velocities. If the asteroid moves
     * off the designated screen boundaries, it is destroyed.
     *  are used to determine if the asteroid has gone off the screen.
     *.
     */
    @Override
    public void updateActor() {
        updatePosition();

        // Destroy asteroid if it moves off-screen
        if (getLayoutY() > 800 || getLayoutX() < -DEFAULT_SIZE || getLayoutX() > 1300) { // Adjust for screen boundaries
            destroy();
        }
    }

    /**
     * Gets the default size of the asteroid.
     * @return DEFAULT_SIZE
     */
    public static int getDefaultSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Sets the default size of the asteroid.
     * {@param destroy} The new default size for the asteroid.
     */
    @Override
    public void takeDamage() {
        destroy(); // Destroy asteroid when hit
    }
}
