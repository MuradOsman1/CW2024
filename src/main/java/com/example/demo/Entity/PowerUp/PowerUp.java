package com.example.demo.Entity.PowerUp;

import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.Level.LevelParent;

/**
 * Represents a power-up in the game.
 */
public abstract class PowerUp extends ActiveActorDestructible {
    private final LevelParent level;
    private static final int IMAGE_HEIGHT = 40;


    /**
     * Constructs a new PowerUp instance.
     *
     * @param imageName    the name of the image file for the power-up
     * @param initialXPos  the initial X position of the power-up
     * @param initialYPos  the initial Y position of the power-up
     * @param level        the level in which the power-up exists
     */
    public PowerUp(String imageName, double initialXPos, double initialYPos, LevelParent level) {
        super(imageName, IMAGE_HEIGHT, initialXPos, initialYPos);

        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }

        this.level = level; // Assign the level reference
    }

    /**
     * Gets the level in which the power-up exists.
     *
     * @return the level
     */
    protected LevelParent getLevel() {
        return level; // Provide access to the containing level
    }

    /**
     * Moves the power-up vertically.
     */
    public void moveVertically(double verticalVelocity) {
        setLayoutY(getLayoutY() + verticalVelocity);
    }

    /**
     * Moves the power-up vertically.
     *
     */
    @Override
    public void updatePosition() {
        moveVertically(5);
    }

    /**
     * Updates the state of the power-up.
     * Calls the {@link #updatePosition()} method to move the power-up vertically.
     * If power-up is out of bounds, it is destroyed.
     */
    @Override
    public void updateActor() {
        updatePosition();

        if (getLayoutX() < -52) {
            destroy();
        }
    }

    /**
     * Applies the effect of the power-up to the specified user plane.
     *
     * @param playerPlane the user plane to which the effect is applied
     */
    public abstract void applyEffect(PlayerPlane playerPlane);


    /**
     * If the power-up takes damage it destroys the power-up.
     */
    @Override
    public void takeDamage() {
        destroy();
    }
}