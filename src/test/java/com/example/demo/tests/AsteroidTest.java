package com.example.demo.tests;

import com.example.demo.Asteroid;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsteroidTest {

    private Asteroid asteroid;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initialize JavaFX Toolkit
    }

    @BeforeEach
    void setUp() {
        asteroid = new Asteroid("asteroid.png", 100, 100, 45, 10);
    }

    @Test
    void testUpdatePosition() {
        double initialX = asteroid.getTranslateX();
        double initialY = asteroid.getTranslateY();
        asteroid.updatePosition();
        assertNotEquals(initialX, asteroid.getTranslateX());
        assertNotEquals(initialY, asteroid.getTranslateY());
    }

    @Test
    void testDestroy() {
        asteroid.destroy();
        assertTrue(asteroid.isDestroyed());
    }
}
