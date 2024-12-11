package com.example.demo;


public class HealthPowerUp extends PowerUp {

    private static final String IMAGE_NAME = "HealthPowerUP.png";

    public HealthPowerUp(double initialXPos, double initialYPos, LevelParent level) {
        super(IMAGE_NAME, initialXPos, initialYPos, level); // Correctly match the PowerUp constructor
    }

    @Override
    public void applyEffect(UserPlane userPlane) {
        getLevel().addHealthToUser(); // Increase user health
    }
}
