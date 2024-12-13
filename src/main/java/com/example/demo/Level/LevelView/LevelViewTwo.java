package com.example.demo.Level.LevelView;

import com.example.demo.Entity.Boss.BossHealth;
import com.example.demo.Level.levels.LevelTwo;
import com.example.demo.UserInterface.ShieldImage;
import javafx.scene.Group;
/**
 * Represents the view for {@link LevelTwo}.
 */
public class LevelViewTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1100;
	private static final int SHIELD_Y_POSITION = 500;
	private static final double BOSS_HEALTH_X_POSITION = 1150;
	private static final double BOSS_HEALTH_Y_POSITION = 75;
	private final Group root;
	private final ShieldImage shieldImage;
	private final BossHealth bossHealth;

	/**
	 * Constructs a new {@link LevelViewTwo} instance.
	 *
	 * @param root           the root group
	 * @param heartsToDisplay the number of hearts to display
	 */
	public LevelViewTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealth = new BossHealth(BOSS_HEALTH_X_POSITION, BOSS_HEALTH_Y_POSITION, 5);
		addImagesToRoot();
	}

	/**
	 * Adds images to the root group.
	 */

	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}



	/**
	 * Shows the shield image.
	 * {@link ShieldImage#showShield()}
	 */
	public void showShield() {
		shieldImage.showShield();
	}


	/**
	 * Shows the shield image.
	 * {@link ShieldImage#hideShield()}
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

}