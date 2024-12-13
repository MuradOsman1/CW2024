package com.example.demo.Level.levels;

import com.example.demo.Entity.Enemy.EnemyPlane;
import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Entity.Player.PlayerPlane;

/**
 *
 * Represents the first level of the game. This class extends the LevelParent class
 * {@link LevelParent}
 * and provides specific behavior, configuration, and functionality for the first level.
 * It manages enemy spawning, game-over conditions, and transitions to the next level.
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.png";
	private static final String NEXT_LEVEL = "com.example.demo.Level.levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 2;
	private static final int KILLS_TO_ADVANCE = 6;
	private static final double ENEMY_SPAWN_PROBABILITY = .30;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
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
		}
		else if (userHasReachedKillTarget()) {
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
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
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
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
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

	@Override
	protected void misc() {

	}

}