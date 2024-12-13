package com.example.demo.Level.LevelView;

import javafx.scene.Group;

/**
 * This class represents the view for the fourth level.
 */
public class LevelFourView extends LevelView {

    public LevelFourView(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
    }

    /**
     * This method is called when the player has completed the level.
     * {@link LevelView#showWinImage()}
     */
    @Override
    public void showWinImage() {
        System.out.println("Level Four Complete!");
        super.showWinImage();
    }
}
