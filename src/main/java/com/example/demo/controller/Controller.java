package com.example.demo.controller;

import com.example.demo.*;
import javafx.stage.Stage;

public class Controller implements EventListener {
	private final Stage stage;

	private LevelView currentLevelView;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() {

		LevelParent level = new LevelOne(stage.getHeight(), stage.getWidth());
		level.setEventListener(this);
		stage.setScene(level.initializeScene());
		stage.show();
		level.startGame();
	}

	@Override
	public void handleEvent(GameEvent event) {
		switch (event.getEventType()) {
			case LEVEL_COMPLETE:
				handleLevelComplete(event.getData());
				break;
			case GAME_OVER:
				handleGameOver();
				break;
			case GAME_WIN:
				handleGameWin();
				break;
		}
	}

	private void handleLevelComplete(String nextLevelClassName) {
		try {
			Class<?> nextLevelClass = Class.forName(nextLevelClassName);
			LevelParent nextLevel = (LevelParent) nextLevelClass
					.getConstructor(double.class, double.class)
					.newInstance(stage.getHeight(), stage.getWidth());
			nextLevel.setEventListener(this);
			stage.setScene(nextLevel.initializeScene());
			currentLevelView = nextLevel.getLevelView();
			nextLevel.setEventListener(this);
			nextLevel.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleGameOver() { // Dummy view for now
		currentLevelView.showGameOverImage(); // Display the game over image
		// Optionally, transition back to the main menu or restart
	}

	private void handleGameWin() {
		System.out.println("Congratulations, You Win!");
		LevelView levelView = new LevelView(new javafx.scene.Group(), 0); // Dummy view for now
		levelView.showWinImage(); // Display the win image
		// Optionally, transition to a win screen or restart
	}
}
