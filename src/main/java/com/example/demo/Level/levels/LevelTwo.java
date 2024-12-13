package com.example.demo.Level.levels;

import com.example.demo.Entity.Boss.Boss;
import com.example.demo.Entity.Boss.BossHealth;
import com.example.demo.Level.LevelParent;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewTwo;

/**
 *
 * Represents the second level of the game. This class extends the LevelParent class
 * {@link LevelParent}
 * and provides specific behavior, configuration, and functionality for the second level.
 * It manages enemy spawning, game-over conditions, and transitions to the next level.
 */
public class LevelTwo extends LevelParent {


	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final String NEXT_LEVEL = "com.example.demo.Level.levels.LevelThree";
	private final Boss boss;
	private final BossHealth bossHealth;
	private LevelViewTwo levelView;

	/**
	 * Constructs a new {@link LevelTwo} instance.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth  the width of the screen
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
		this.bossHealth = new BossHealth(850, 25, boss.getHealth());
	}

	/**
	 * Initializes friendly units in the game.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		getRoot().getChildren().add(bossHealth.getContainer());
	}

	/**
	 * Checks if the game is over by evaluating the user's and boss's status.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			stopLevel();
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units in the game.
	 * {@link Boss} is the only enemy unit in this level.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
		bossHealth.updateHealth(boss.getHealth());
	}

	/**
	 * Instantiates the view for {@link LevelTwo}.
	 *
	 * @return the {@link LevelViewTwo} instance
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	/**
	 * Updates the shield image based on the boss's shield status.
	 * {@link Boss}
	 * {@link LevelViewTwo#showShield()}
	 */
	private void updateShieldImage() {
		if (boss.getIsShielded()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}

	/**
	 * Miscellaneous updates specific to {@link LevelTwo}. Calls the {@link LevelTwo#updateShieldImage()}
	 */
	@Override
	protected void misc() {
		updateShieldImage();
	}
}