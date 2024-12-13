package com.example.demo.controller;

import com.example.demo.EventHandler.EventListener;
import com.example.demo.EventHandler.GameEvent;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.levels.LevelOne;
import com.example.demo.Level.LevelParent;
import com.example.demo.UserInterface.GameOverImage;
import javafx.stage.Stage;

public class Controller implements EventListener {
	private final Stage stage;

	private LevelView currentLevelView;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the initial {@link LevelParent} and sets up the game environment.
	 *
	 */
	public void launchGame() {

		LevelParent level = new LevelOne(stage.getHeight(), stage.getWidth());
		level.setEventListener(this);
		stage.setScene(level.initializeScene());
		stage.show();
		level.startGame();
	}

	/**
	 * Handles game events and takes appropriate actions based on the event type.
	 *
	 * @param event the {@link GameEvent} containing the type of event and associated data
	 */
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

	/**
	 * Handles the completion of a level by transitioning to the next level.
	 * @param nextLevelClassName is the name of the next class to be loaded for the next level
	 */
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

	/**
	 * Handles the game over event by displaying the game over image{@link GameOverImage} and transitioning back to the main menu.
	 */
	private void handleGameOver() {
		currentLevelView.showGameOverImage(); // Display the game over image
		// Optionally, transition back to the main menu or restart
	}



	/**
	 * Handles the end-of-game win scenario by displaying a congratulatory message from {@link LevelView}
	 */
	private void handleGameWin() {
		System.out.println("Congratulations, You Win!");
		LevelView levelView = new LevelView(new javafx.scene.Group(), 0); // Dummy view for now
		levelView.showWinImage(); // Display the win image
		// Optionally, transition to a win screen or restart
	}
}
