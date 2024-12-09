package com.example.demo;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";
    private static final String NEXT_LEVEL = "com.example.demo.LevelFour";
    private static final int TOTAL_ENEMIES = 3; // Number of enemies to spawn
    private static final int KILLS_TO_ADVANCE = 2; // Increased number of kills to advance
    private static final double ENEMY_SPAWN_PROBABILITY = 0.4; // Spawn probability
    private static final int PLAYER_INITIAL_HEALTH = 3; // Player has 3 lives

    // Asteroid configuration
    private static final int ASTEROIDS_PER_SPAWN = 1;
    private static final int ASTEROID_SPAWN_INTERVAL = 10; // 1 second interval (assuming 60fps)
    private static final double ASTEROID_SPEED = 10;
    private static final double ASTEROID_ANGLE = 120; // Falling at 30-degree angle

    private int frameCounter = 0;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            stopLevel();
            goToNextLevel(NEXT_LEVEL);
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewThree(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

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

    private void spawnAsteroids() {
        for (int i = 0; i < ASTEROIDS_PER_SPAWN; i++) {
            double spawnX = Math.random() * getScreenWidth();
            double spawnY = -Asteroid.getDefaultSize();

            // Create a new asteroid
            Asteroid asteroid = new Asteroid("asteroid.png", spawnX, spawnY, ASTEROID_ANGLE, ASTEROID_SPEED);
            addAsteroid(asteroid);
        }
    }

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
