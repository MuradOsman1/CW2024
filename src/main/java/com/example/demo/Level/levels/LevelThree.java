package com.example.demo.Level.levels;

import com.example.demo.Asteroid;
import com.example.demo.Entity.Enemy.EnemyPlane;
import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.Level.LevelParent;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewThree;

/**
 *
 * Represents the third level of the game. This class extends the LevelParent class
 * {@link LevelParent}
 * and provides specific behavior, configuration, and functionality for the third level.
 * It manages enemy spawning, game-over conditions, and transitions to the next level.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";
    private static final String NEXT_LEVEL = "com.example.demo.Level.levels.LevelFour";
    private static final int TOTAL_ENEMIES = 3; // Number of enemies to spawn
    private static final int KILLS_TO_ADVANCE = 10; // Increased number of kills to advance
    private static final double ENEMY_SPAWN_PROBABILITY = 0.4; // Spawn probability
    private static final int PLAYER_INITIAL_HEALTH = 3; // Player has 3 lives

    // Asteroid configuration
    private static final int ASTEROIDS_PER_SPAWN = 1;
    private static final int ASTEROID_SPAWN_INTERVAL = 20; // 1 second interval (assuming 60fps)
    private static final double ASTEROID_SPEED = 10;
    private static final double ASTEROID_ANGLE = 120; // Falling at 30-degree angle

    private int frameCounter = 0;

    /**
     * Constructs a new LevelThree instance.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the user has been destroyed or if the user has reached the kill target.
     * {@link LevelParent#checkIfGameOver()}
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            stopLevel();
            goToNextLevel(NEXT_LEVEL);
        }
    }

    /**
     * Initializes the friendly units for the level.
     * {@link LevelParent#initializeFriendlyUnits()}
     *
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemies based on a probability.
     * {@link LevelParent#spawnEnemyUnits()}
     * {@link LevelParent#getCurrentNumberOfEnemies()}
     * {@link LevelParent#addEnemyUnit(ActiveActorDestructible)}
     * {@link EnemyPlane )}
     */
    @Override
    protected void spawnEnemyUnits() {
        // Continuously spawn enemies up to the total limit
        while (getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Creates a new instance of the LevelView class.
     * {@link LevelParent#instantiateLevelView()}
     * {@link LevelView}
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewThree(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the user has reached the number of kills required to advance to the next level.
     * {@link LevelParent#userIsDestroyed()}
     * {@link LevelParent#getUser()}
     * {@link PlayerPlane#getNumberOfKills()}
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Performs miscellaneous operations specific to LevelThree. This method is invoked
     * periodically during the game loop and handles various non-exclusive game logic tasks.
     * In the case of Level Three it is used to handle how often to spawn the asteroids and their collisions
     * {@link LevelThree#spawnAsteroids()}
     * {@link LevelThree#handleProjectileCollisions()}
     */
    @Override
    protected void misc() {
        frameCounter++;

        // Spawn asteroids periodically
        if (frameCounter % ASTEROID_SPAWN_INTERVAL == 0) {
            spawnAsteroids();
        }

        // Handle collisions between projectiles and actors
        handleProjectileCollisions();
    }

    /**
     * Spawns asteroids in the game.
     *{@link Asteroid}
     */
    private void spawnAsteroids() {
        for (int i = 0; i < ASTEROIDS_PER_SPAWN; i++) {
            double spawnX = Math.random() * getScreenWidth();
            double spawnY = -Asteroid.getDefaultSize();

            // Create a new asteroid
            Asteroid asteroid = new Asteroid("asteroid.png", spawnX, spawnY, ASTEROID_ANGLE, ASTEROID_SPEED);
            addAsteroid(asteroid);
        }
    }

    /**
     * Handles collisions between projectiles and actors in the game.
     */
    private void handleProjectileCollisions() {
        // User projectiles vs enemies and asteroids
        for (ActiveActorDestructible projectile : getUserProjectiles()) {
            for (ActiveActorDestructible enemy : getEnemyUnits()) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    projectile.takeDamage();
                    enemy.takeDamage();
                    getUser().incrementKillCount();
                }
            }

            // User projectiles vs asteroids
            for (ActiveActorDestructible asteroid : getEnemyUnits()) {
                if (projectile.getBoundsInParent().intersects(asteroid.getBoundsInParent())) {
                    projectile.takeDamage();
                    asteroid.takeDamage(); // Asteroid is destroyed
                }
            }
        }

        // Enemy projectiles vs user
        for (ActiveActorDestructible enemyProjectile : getEnemyProjectiles()) {
            if (enemyProjectile.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
                enemyProjectile.takeDamage();
                getUser().takeDamage();
            }
        }
    }
}
