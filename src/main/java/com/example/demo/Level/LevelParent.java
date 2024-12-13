package com.example.demo.Level;

import java.util.*;


import com.example.demo.CollisionHandler.CollisionHandler;
import com.example.demo.Entity.Enemy.EnemyPlane;
import com.example.demo.Entity.Entities.ActiveActor;
import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Entities.FighterPlane;
import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.EventHandler.EventListener;
import com.example.demo.EventHandler.EventNotifier;
import com.example.demo.EventHandler.GameEvent;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.levels.LevelFour;
import com.example.demo.Level.levels.LevelOne;
import com.example.demo.Level.levels.LevelThree;
import com.example.demo.Level.levels.LevelTwo;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;
/**
 * Base class for all levels in the game.
 * Provides common functionality such as actor updates, scene initialization and game state management.
 *
 * @see LevelOne
 * @see LevelTwo
 * @see LevelThree
 * @see LevelFour
 */
public abstract class LevelParent extends EventNotifier {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 35;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final PlayerPlane user;
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

	/**
	 * Constructor for LevelParent.
	 *
	 * @param backgroundImageName The background image for the level.
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 * @param playerInitialHealth The initial health of the player.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.user = new PlayerPlane(background, playerInitialHealth);
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

	/**
	 * Returns the level view.
	 * {@link LevelView}
	 */
	public LevelView getLevelView() {
		return levelView;
	}

	/**
	 * Abstract method that initializes the friendly units for the level.
	 *  * {@link FighterPlane}
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method that is utilized for checking if the level is over
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method that is utilized for spawning enemy units
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method that is utilized for instantiating the level view
	 */
	protected abstract LevelView instantiateLevelView();


	protected abstract void misc();

	/**
	 * Initializes and configures the game scene for the level. This includes setting up the background,
	 * initializing friendly units, and displaying the user's heart display on the game interface.
	 * {@link Scene}
	 * {@link LevelView#showHeartDisplay()}
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game timeline and enables scene updates.
	 * {@link Timeline}
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 *  Performs various game logic operations using the Event Listener to go to the next level
	 * {@link EventListener#handleEvent(GameEvent)}
	 *
	 */
	protected void goToNextLevel(String nextLevelClassName) {
		if (getEventListener() != null) {
			getEventListener().handleEvent(new GameEvent(GameEvent.EventType.LEVEL_COMPLETE, nextLevelClassName));
		}
	}

	/**
	 * Updates the game scene by spawning enemy units, updating actors, handling collisions, generating enemy fire, updating the kill count.
	 */
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

	/**
	 * Initializes the game timeline with a specified delay between game loops.
	 * {@link Timeline}
	 *
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image for the game scene.
	 * {@link ImageView}
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		root.getChildren().add(background);
	}

	/**
	 * Spawns a projectile from the user's plane.
	 * {@link FighterPlane#fireProjectile()}
	 * {@link ActiveActorDestructible}
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();

		if (projectile == null)
			return;

		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy projectiles based on the enemy units.
	 * {@link FighterPlane#fireProjectile()}
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}


	/**
	 * Spawns an enemy projectile from the enemy plane.
	 * {@link FighterPlane#fireProjectile()}
	 * {@link ActiveActorDestructible}
	 * {@link EnemyPlane}
	 * @param projectile the projectile to be spawned.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}
	public LevelParent getLevel() {
		return this; // Return the current level instance
	}

	/**
	 * Adds health to the user. Used for the Health Power up.
	 * {@link LevelView#addHeart()}
	 */
	public void addHealthToUser() {
		getUser().addHealth(); // Call the addHealth method in UserPlane
		levelView.addHeart(); // Update the heart display in the LevelView
	}


	/**
	 * Updates the actors in the game scene.
	 * {@link ActiveActorDestructible}
	 */
	protected void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
		asteroids.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Removes destroyed actors from the game scene.
	 * {@link ActiveActor}
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(asteroids);
	}

	/**
	 * Removes all actors that are marked as destroyed from the provided list of actors
	 * and from the game scene.
	 *
	 * @param actors the list of {@link ActiveActorDestructible} actors to check for
	 *               removal if marked as destroyed.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between friendly units and enemy units.
	 * {@link CollisionHandler#handleCollisions(List, List)}
	 */
	private void handlePlaneCollisions() {
		collisionHandler.handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 * {@link CollisionHandler#handleCollisions(List, List)}
	 */
	private void handleUserProjectileCollisions() {
		collisionHandler.handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 * {@link CollisionHandler#handleCollisions(List, List)}
	 */
	private void handleEnemyProjectileCollisions() {
		collisionHandler.handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Handles collisions between user projectiles and asteroids.
	 * {@link CollisionHandler#handleCollisions(List, List)}
	 */
	private void handleAsteroidCollisions() {
		collisionHandler.handleCollisions(userProjectiles, asteroids);
		collisionHandler.handleAsteroidCollisions(user, asteroids);
	}


	/**
	 * Updates the level view by removing hearts from the user's health.
	 * {@link LevelView#removeHearts(int)}
	 * {@link PlayerPlane#getHealth()}
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the kill count by incrementing it based on the difference between the current number of enemies and the number of enemies in the enemyUnits list.
	 * {@link PlayerPlane#incrementKillCount()}
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}


	/**
	 * Handles the game over condition by stopping the timeline and displaying the game over image.
	 * {@link Timeline#stop()}
	 * {@link LevelView#showWinImage()}
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();

	}

	/**
	 * Handles the game over condition by stopping the timeline and displaying the game over image.
	 * {@link LevelView#showGameOverImage()}
	 * {@link Timeline#stop()}
	 */
	protected void loseGame() {
		levelView.showGameOverImage();
		timeline.stop();
	}

	/**
	 * Stops the game timeline.
	 *
	 */
	protected void stopLevel() {
		timeline.stop();
	}

	/**
	 * Checks if the game is over by comparing the current number of enemies with the initial number of enemies.
	 * {@link PlayerPlane}
	 */
	protected PlayerPlane getUser() {
		return user;
	}

	/**
	 * Returns the root group of the game scene.
	 * {@link Group}
	 */
	protected Group getRoot() {
		return root;
	}


	/**
	 * Returns the current number of enemies in the enemyUnits list.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Returns the initial number of enemies in the enemyUnits list.
	 * {@link ActiveActorDestructible}
	 * @param enemy Enemy Units
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Returns the initial number of Asteroids in the asteroids list to add them.
	 * {@link ActiveActorDestructible}
	 * @param projectile The asteroids
	 */
	protected void addAsteroid(ActiveActorDestructible projectile) {
		asteroids.add(projectile);
		root.getChildren().add(projectile);
	}

	/**
	 * Returns the maximum position an enemy can spawn in
	 * @return enemyMaximumXPosition the maximum position.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Returns the width of the screen
	 * @return screenWidth the width of the screen
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Boolean method to check if the user is destroyed
	 * {@link PlayerPlane}
	 * @return userIsDestroyed if the user is destroyed.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Gets the current number of enemies in the enemyUnits list.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Returns the list of user projectiles.
	 * {@link ActiveActorDestructible}
	 * @return userProjectiles the list of user projectiles.
	 */
	protected List<ActiveActorDestructible> getUserProjectiles() {
		return userProjectiles;
	}

	/**
	 * Returns the list of enemy projectiles.
	 * {@link ActiveActorDestructible}
	 * @return enemyUnits the list of enemy units.
	 */
	protected List<ActiveActorDestructible> getEnemyUnits() {
		return enemyUnits;
	}

	/**
	 * Returns the list of enemy projectiles.
	 * {@link ActiveActorDestructible}
	 * @return enemyProjectiles the list of enemy projectiles.
	 */
	protected List<ActiveActorDestructible> getEnemyProjectiles() {
		return enemyProjectiles;
	}

}