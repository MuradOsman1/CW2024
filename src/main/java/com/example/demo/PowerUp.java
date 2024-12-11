package com.example.demo;


public abstract class PowerUp extends ActiveActorDestructible {
    private final LevelParent level;
    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private static final int IMAGE_HEIGHT = 40;
    private static final double VERTICAL_VELOCITY = -5;

    public PowerUp(String imageName, double initialXPos, double initialYPos, LevelParent level) {
        super(imageName, IMAGE_HEIGHT, initialXPos, initialYPos);

        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }

        this.level = level; // Assign the level reference
    }


    protected LevelParent getLevel() {
        return level; // Provide access to the containing level
    }

    @Override
    public void updatePosition() {
        moveVertically(5);
    }

    @Override
    public void updateActor() {
        updatePosition();

        if (getLayoutX() < -52) {
            destroy();
        }
    }
    public abstract void applyEffect(UserPlane userPlane);


    @Override
    public void takeDamage() {
        destroy();
    }
}