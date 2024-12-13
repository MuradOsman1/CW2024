package com.example.demo.Entity.PowerUp;

import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.Level.LevelParent;

/**
 * Represents a power-up that increases the fire rate of the user's plane.
 * This class extends {@link PowerUp} and provides functionality
 * for applying the fire rate boost effect to the user's plane.
 */
public class FireRatePowerUp extends PowerUp {

    private static final String IMAGE_NAME = "FireRate.png";

    public FireRatePowerUp(double initialXPos, double initialYPos, LevelParent level) {
        super(IMAGE_NAME, initialXPos, initialYPos, level); // Correctly match the PowerUp constructor
    }

    @Override
    public void applyEffect(PlayerPlane playerPlane) {
        playerPlane.activateFireRateBoost(5_000); // Boost fire rate for 5 seconds
    }
}
