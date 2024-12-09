package com.example.demo;

import javafx.scene.Group;

public class LevelViewThree extends LevelView {

    public LevelViewThree(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
    }

    @Override
    public void showWinImage() {
        System.out.println("Level Three Complete! Proceeding to the next level...");
        super.showWinImage();
    }

    @Override
    public void showGameOverImage() {
        System.out.println("Game Over in Level Three.");
        super.showGameOverImage();
    }
}
