package com.example.demo;

public class Asteroid extends ActiveActorDestructible {

    private static final int DEFAULT_SIZE = 120;  // Default asteroid size
    private double xVelocity;
    private double yVelocity;

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

    @Override
    public void updatePosition() {
        moveHorizontally(xVelocity); // Diagonal horizontal movement
        moveVertically(yVelocity);  // Vertical movement
    }

    @Override
    public void updateActor() {
        updatePosition();

        // Destroy asteroid if it moves off-screen
        if (getLayoutY() > 800 || getLayoutX() < -DEFAULT_SIZE || getLayoutX() > 1300) { // Adjust for screen boundaries
            destroy();
        }
    }

    public static int getDefaultSize() {
        return DEFAULT_SIZE;
    }

    @Override
    public void takeDamage() {
        destroy(); // Destroy asteroid when hit
    }
}
