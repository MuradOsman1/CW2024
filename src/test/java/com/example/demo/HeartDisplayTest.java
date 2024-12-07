package com.example.demo;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeartDisplayTest {

    private HeartDisplay heartDisplay;

    @BeforeAll
    static void setupJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Initialize JavaFX runtime
    }

    @BeforeEach
    void setUp() {
        heartDisplay = new HeartDisplay(100, 200, 3); // 3 hearts at position (100, 200)
    }

    @Test
    void testInitialization() {
        HBox container = heartDisplay.getContainer();

        // Verify container position
        assertEquals(100, container.getLayoutX(), "Container X position should match the given value.");
        assertEquals(200, container.getLayoutY(), "Container Y position should match the given value.");

        // Verify number of hearts
        assertEquals(3, container.getChildren().size(), "Number of hearts should match the given value.");

        // Verify each heart's properties
        for (int i = 0; i < 3; i++) {
            assertTrue(container.getChildren().get(i) instanceof ImageView, "Each child should be an ImageView.");
            ImageView heart = (ImageView) container.getChildren().get(i);
            assertEquals(50, heart.getFitHeight(), "Each heart's height should match the given value.");
            assertTrue(heart.isPreserveRatio(), "Each heart's ratio preservation should be true.");
        }
    }

    @Test
    void testRemoveHeart() {
        HBox container = heartDisplay.getContainer();

        // Verify initial number of hearts
        assertEquals(3, container.getChildren().size(), "Number of hearts should initially be 3.");

        // Remove one heart
        heartDisplay.removeHeart();
        assertEquals(2, container.getChildren().size(), "Number of hearts should decrease by 1 after removal.");

        // Remove all hearts
        heartDisplay.removeHeart();
        heartDisplay.removeHeart();
        assertEquals(0, container.getChildren().size(), "Number of hearts should be 0 after removing all hearts.");

        // Attempt to remove heart when none exist
        heartDisplay.removeHeart();
        assertEquals(0, container.getChildren().size(), "Number of hearts should remain 0 when no hearts are left.");
    }
}
