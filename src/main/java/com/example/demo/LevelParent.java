package com.example.demo;

import java.util.*;


import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent extends EventNotifier {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final CollisionHandler collisionHandler = CollisionHandler.getInstance();

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> asteroids;

	private final LevelView levelView;

	private int currentNumberOfEnemies;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.user = new UserPlane(background, playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.asteroids = new ArrayList<>();

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	public LevelView getLevelView() {
		return levelView;
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	protected abstract void misc();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	protected void goToNextLevel(String nextLevelClassName) {
		if (getEventListener() != null) {
			getEventListener().handleEvent(new GameEvent(GameEvent.EventType.LEVEL_COMPLETE, nextLevelClassName));
		}
	}
	protected void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleAsteroidCollisions();
		removeAllDestroyedActors();

		updateKillCount();
		updateLevelView();
		checkIfGameOver();
		fireProjectile();
		misc();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		root.getChildren().add(background);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();

		if (projectile == null)
			return;

		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}
	public LevelParent getLevel() {
		return this; // Return the current level instance
	}

	public void addHealthToUser() {
		getUser().addHealth(); // Call the addHealth method in UserPlane
		levelView.addHeart(); // Update the heart display in the LevelView
	}






	protected void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
		asteroids.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(asteroids);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		collisionHandler.handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		collisionHandler.handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		collisionHandler.handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleAsteroidCollisions() {
		collisionHandler.handleCollisions(userProjectiles, asteroids);
		collisionHandler.handleAsteroidCollisions(user, asteroids);
	}






	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		if (getEventListener() != null) {
			getEventListener().handleEvent(new GameEvent(GameEvent.EventType.GAME_WIN));
		}
	}
	protected void loseGame() {
		timeline.stop();
		if (getEventListener() != null) {
			getEventListener().handleEvent(new GameEvent(GameEvent.EventType.GAME_OVER));
		}
	}

	protected void stopLevel() {
		timeline.stop();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected void addAsteroid(ActiveActorDestructible projectile) {
		asteroids.add(projectile);
		root.getChildren().add(projectile);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	protected List<ActiveActorDestructible> getUserProjectiles() {
		return userProjectiles;
	}

	protected List<ActiveActorDestructible> getEnemyUnits() {
		return enemyUnits;
	}

	protected List<ActiveActorDestructible> getEnemyProjectiles() {
		return enemyProjectiles;
	}

}