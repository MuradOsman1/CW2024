package com.example.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.png";
    private static final int TOTAL_ENEMIES = 4;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.30;
    private static final int KILLS_TO_ADVANCE = 1;
    private static final int PLAYER_INITIAL_HEALTH = 3;

    private static final int POWERUP_SPAWN_INTERVAL = 20; // Spawn power-ups every 500 frames
    private int frameCounter = 0; // To track frames for power-up spawning



    private List<PowerUp> powerUps;

    public LevelFour(double screenHeight, double screenWidth) {
        // Pass all necessary parameters to LevelParent's constructor
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        powerUps = new ArrayList<>();
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget()) {
            stopLevel();
            winGame();
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);


            }
        }
        // Spawn power-ups periodically
    }

    private void spawnPowerUp() {
        double spawnX = Math.random() * getScreenWidth();
        double spawnY = -50;

        PowerUp powerUp = Math.random() < 0.5
                ? new HealthPowerUp(spawnX, spawnY, this) // Pass 'this' as the level reference
                : new FireRatePowerUp(spawnX, spawnY, this);
        System.out.println("Power-up created at: X=" + spawnX + ", Y=" + spawnY);

        getRoot().getChildren().add(powerUp);
        powerUps.add(powerUp);
    }


    @Override
    protected void updateActors() {
        super.updateActors();

        // Check for collisions with power-ups
        for (Iterator<PowerUp> iterator = powerUps.iterator(); iterator.hasNext(); ) {
            PowerUp powerUp = iterator.next();
            powerUp.updateActor(); // Update the position of the power-up

            if (powerUp.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
                System.out.println("Power-up collected!");
                powerUp.applyEffect(getUser()); // Apply the effect to the user
                getRoot().getChildren().remove(powerUp); // Remove the power-up from the game
                iterator.remove(); // Remove from the active list
            }
        }
    }

    @Override
    protected void updateScene() {
        super.updateScene();
        System.out.println("updateScene called, frameCounter: " + frameCounter);
        frameCounter++;
        if (frameCounter % POWERUP_SPAWN_INTERVAL == 0) {
            System.out.println("Spawning power-up");
            spawnPowerUp();
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void misc() {
        // Custom logic specific to LevelFour (if needed)
    }
}
