package com.example.demo.controller;

import com.example.demo.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1280;
	private static final int SCREEN_HEIGHT = 720;
	private static final String TITLE = "Sky Battle";

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

	public static void main(String[] args) {
		launch();
	}
}
