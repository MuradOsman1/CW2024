package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1100;
	private static final int SHIELD_Y_POSITION = 500;
	private static final double BOSS_HEALTH_X_POSITION = 1150;
	private static final double BOSS_HEALTH_Y_POSITION = 75;
	private final Group root;
	private final ShieldImage shieldImage;
	private final BossHealth bossHealth;

	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealth = new BossHealth(BOSS_HEALTH_X_POSITION, BOSS_HEALTH_Y_POSITION, 5);
		addImagesToRoot();
	}

	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	public void showBossHealth() {
		root.getChildren().add(bossHealth.getContainer());
	}

	public void updateBossHealth(int newHealth) {
		bossHealth.updateHealth(newHealth);
	}


	public void showShield() {
		shieldImage.showShield();
	}



	public void hideShield() {
		shieldImage.hideShield();
	}

}