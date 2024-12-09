//package com.example.demo;
//
//public class LevelFour extends LevelParent {
//    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.png";
//    private static final String NEXT_LEVEL = "com.example.demo.LevelFive";
//    private static final int TOTAL_ENEMIES = 3;
//    private static final int KILLS_TO_ADVANCE = 15;
//    private static final double ENEMY_SPAWN_PROBABILITY = 0.3;
//    private static final int PLAYER_INITIAL_HEALTH = 5;
//    private static final int POWER_UP_SPAWN_INTERVAL = 300;
//
//    private int powerUpSpawnCounter = 0;
//
//    public LevelFour(double screenHeight, double screenWidth) {
//        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
//    }
//
//    @Override
//    protected void checkIfGameOver() {
//        if (userIsDestroyed()) {
//            loseGame();
//        } else if (userHasReachedKillTarget()) {
//            stopLevel();
//            goToNextLevel(NEXT_LEVEL);
//        }
//    }
//
//    @Override
//    protected void initializeFriendlyUnits() {
//        getRoot().getChildren().add(getUser());
//    }
//
//    @Override
//    protected void spawnEnemyUnits() {
//        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
//        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
//            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
//                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
//                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
//                addEnemyUnit(newEnemy);
//            }
//        }
//    }
//
//    @Override
//    protected LevelView instantiateLevelView() {
//        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
//    }
//
//    @Override
//    protected void misc() {
//        powerUpSpawnCounter++;
//
//        if (powerUpSpawnCounter % POWER_UP_SPAWN_INTERVAL == 0) {
//            spawnPowerUp();
//        }
//    }
//
//
//
//    private void spawnPowerUp() {
//        double spawnX = getScreenWidth();
//        double spawnY = Math.random() * getEnemyMaximumYPosition();
//
//        ActiveActorDestructible powerUp;
//        if (Math.random() < 0.5) {
//            powerUp = new HeartPowerUp(spawnX, spawnY);
//        } else {
//            powerUp = new FireRatePowerUp(spawnX, spawnY);
//        }
//
//        addPowerUp(powerUp);
//    }
//
//    private void addPowerUp(ActiveActorDestructible powerUp) {
//        // This method can be added to LevelParent if needed
//        getRoot().getChildren().add(powerUp);
//    }
//
//    private boolean userHasReachedKillTarget() {
//        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
//    }
//}