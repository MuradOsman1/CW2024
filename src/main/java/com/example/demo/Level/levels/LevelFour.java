package com.example.demo.Level.levels;

import com.example.demo.Entity.Enemy.EnemyPlane;
import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.Entity.PowerUp.FireRatePowerUp;
import com.example.demo.Entity.PowerUp.HealthPowerUp;
import com.example.demo.Entity.PowerUp.PowerUp;
import com.example.demo.Level.LevelParent;
import com.example.demo.Level.LevelView.LevelView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the fourth level of the game.
 * Includes power-up functionality for increasing health and enhancing the user's fire rate.
 *
 * @see LevelParent
 * @see PowerUp
 */
public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.png";
    private static final int TOTAL_ENEMIES = 4;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.30;
    private static final int KILLS_TO_ADVANCE = 20;
    private static final int PLAYER_INITIAL_HEALTH = 3;

    private static final int POWERUP_SPAWN_INTERVAL = 40; // Spawn power-ups every 20 frames
    private int frameCounter = 0; // To track frames for power-up spawning
    private List<PowerUp> powerUps;
    /**
     * Constructor for LevelFour.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth the width of the game screen.
     */
    public LevelFour(double screenHeight, double screenWidth) {
        // Pass all necessary parameters to LevelParent's constructor
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        powerUps = new ArrayList<>();
    }

    /**
     * Checks the game's current state to determine if the game is over.
     *{@link LevelParent#checkIfGameOver()}
     * If the user has reached the specified kill target the game stops, and the win condition is activated.
     */
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

    /**
     * Initializes the friendly units in the level.
     * This method adds the user's plane, represented by the UserPlane instance
     * returned by the getUser() method, to the root Group of the level.
     * {@link LevelParent#initializeFriendlyUnits()}
     *{@link LevelParent#getUser()}
     * Overrides the abstract method from the LevelParent class to set up the
     * friendly units specific to this level configuration.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the user has reached the required number of kills to progress in the level.
     * @return true if the user's number of kills is greater than or equal to the kill target required to advance, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Spawns enemy units into the game based on the configured total enemy limit
     * and spawn probability. The method calculates how many enemies need to be
     * spawned by subtracting the current number of enemies from the total allowed
     * enemies.
     */
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

    /**
     * Spawns a power-up at a random horizontal position off-screen, just above the visible game area.
     * This method generates a `HealthPowerUp` or `FireRatePowerUp` object based on a random probability
     * The choice of power-up type is determined using a 50% probability distribution:
     * - `{@link HealthPowerUp}`: Increases the player's health when collected.
     * - `{@link FireRatePowerUp}`: Temporarily boosts the player's firing rate.
     */
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

    /**
     * Updates the state and position of active actors within the game, as well as manages interactions with power-ups.
     * Overrides the `updateActors` method from the superclass to add custom behavior
     * for handling power-ups in the game.
     *{@link LevelParent#updateActors()}
     * {@link PowerUp#applyEffect(PlayerPlane)}
     * Functionalities performed:
     * - Updates the positions of all active actors as defined in the superclass.
     * - Iterates through the list of active power-ups, updating their positions.
     * - Checks for collisions between the user and power-ups.
     */
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

    /**
     * Updates the scene during each frame of the game loop.
     * This method overrides the `updateScene` functionality in the parent class to add
     * custom behavior specific to the `LevelFour` class. It extends the inherited logic
     * by implementing a mechanic to spawn power-ups at specific frame intervals.
     *{@link LevelParent#updateScene()}
     * {@link LevelFour#spawnPowerUp()}
     **/
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

    /**
     * Instantiates and returns a new LevelView object.
     * This method creates a LevelView instance with the specified root Group
     * and initial player health. It overrides the abstract method from
     * the LevelParent class and provides the LevelFour-specific configuration.
     *{@link LevelParent#instantiateLevelView()}
     * {@link LevelFour#getRoot()}
     * */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void misc() {
        // Custom logic specific to LevelFour (if needed)
    }
}
