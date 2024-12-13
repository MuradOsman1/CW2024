package com.example.demo.Entity.PowerUp;

import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.Level.LevelParent;

/**
 * The HealthPowerUp class represents a power-up that increases the health of the user's plane
 * when collected during the game. It is a specific type of PowerUp with predefined behavior
 * and appearance.
 *{@link PowerUp}
 * Extends the abstract PowerUp class to inherit common power-up behavior, such as updating position
 * and self-destruction.
 */
public class HealthPowerUp extends PowerUp {

    private static final String IMAGE_NAME = "HealthPowerUP.png";

    public HealthPowerUp(double initialXPos, double initialYPos, LevelParent level) {
        super(IMAGE_NAME, initialXPos, initialYPos, level); // Correctly match the PowerUp constructor
    }

    /**
     * Applies the effect of the HealthPowerUp to the specified UserPlane.
     * This method increases the health of the user's plane by modifying the game state,
     * typically by invoking the addHealthToUser method of the associated LevelParent.
     *{@link LevelParent}
     * @param playerPlane the UserPlane object to which the health-increasing effect will be applied
     */
    @Override
    public void applyEffect(PlayerPlane playerPlane) {
        getLevel().addHealthToUser(); // Increase user health
    }
}
