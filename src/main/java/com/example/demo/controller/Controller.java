package com.example.demo.controller;

import java.lang.reflect.Constructor;
import com.example.demo.Observer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class
Controller implements Observer {

	public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne"; // Ensure this is accessible
	private final Stage stage;

	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME); // Start from Level One
		} catch (Exception e) {
			handleException(e, "Failed to launch the game.");
		}
	}

	private void goToLevel(String className) {
		try {
			Class<?> levelClass = Class.forName(className);
			Constructor<?> constructor = levelClass.getConstructor(double.class, double.class);
			currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			currentLevel.addObserver(this);
			Scene scene = currentLevel.initializeScene();
			stage.setScene(scene);
			currentLevel.startGame();
		} catch (Exception e) {
			handleException(e, "Failed to load the level.");
		}
	}

	@Override
	public void update(String nextLevel) {
		goToLevel(nextLevel);
	}

	private void handleException(Exception e, String errorMessage) {
		e.printStackTrace();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(errorMessage);
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
}
