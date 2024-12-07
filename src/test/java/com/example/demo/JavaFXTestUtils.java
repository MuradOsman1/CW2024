package com.example.demo;

import javafx.application.Platform;

public class JavaFXTestUtils {

    public static void initializeJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {

            });
        }
    }
}
