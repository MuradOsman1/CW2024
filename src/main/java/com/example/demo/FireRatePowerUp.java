package com.example.demo;

public class FireRatePowerUp extends PowerUp {

    private static final String IMAGE_NAME = "FireRate.png";

    public FireRatePowerUp(double initialXPos, double initialYPos,LevelParent level) {
        super(IMAGE_NAME, initialXPos, initialYPos, level); // Correctly match the PowerUp constructor
    }

    @Override
    public void applyEffect(UserPlane userPlane) {
        userPlane.activateFireRateBoost(5_000); // Boost fire rate for 6 seconds
    }
}
