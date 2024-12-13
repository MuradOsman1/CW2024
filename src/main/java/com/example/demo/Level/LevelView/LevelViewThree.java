package com.example.demo.Level.LevelView;

import javafx.scene.Group;

/**
 * Represents the view for LevelThree.
 */
public class LevelViewThree extends LevelView {

    /**
     * Constructs a new LevelViewThree instance.
     *
     * @param root           the root group
     * @param heartsToDisplay the number of hearts to display
     */
    public LevelViewThree(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
    }

    /**
     * Shows the win image and prints a message indicating level completion.
     */
    @Override
    public void showWinImage() {
        System.out.println("Level Three Complete! Proceeding to the next level...");
        super.showWinImage();
    }

    /**
     * Shows the game over image and prints a message indicating game over.
     */
    @Override
    public void showGameOverImage() {
        System.out.println("Game Over in Level Three.");
        super.showGameOverImage();
    }
}
