package com.example.demo.tests;

import javafx.application.Platform;

public class JavaFXTestUtils {

    public static void initializeJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {

            });
        }
    }
}
