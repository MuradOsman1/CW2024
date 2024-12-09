package com.example.demo;

import javafx.scene.Group;

public class LevelFourView extends LevelView {

    public LevelFourView(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
    }

    @Override
    public void showWinImage() {
        System.out.println("Level Four Complete!");
        super.showWinImage();
    }
}
