package com.example.demo.controller;

import com.example.demo.UserInterface.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * The main entry point for the Sky Battle game application.
 * This class extends {@link javafx.application.Application} and sets up the primary stage and main menu.
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1280;
	private static final int SCREEN_HEIGHT = 720;
	private static final String TITLE = "Sky Battle";

	/**
	 * Starts the JavaFX application by setting up the primary stage and displaying the main menu.
	 *
	 * @param stage the primary stage for this application
	 */
	@Override
	public void start(Stage stage) {
		try {
			// Set up the stage
			stage.setTitle(TITLE);
			stage.setResizable(false);
			stage.setWidth(SCREEN_WIDTH);
			stage.setHeight(SCREEN_HEIGHT);

			// Show the main menu
			MainMenu mainMenu = new MainMenu();
			mainMenu.start(stage);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An error occurred while launching the application: " + e.getMessage());
		}
	}

	/**
	 * The main method that launches the JavaFX application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch();
	}
}
