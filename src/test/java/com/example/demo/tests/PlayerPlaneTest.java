package com.example.demo.tests;

import com.example.demo.Entity.Player.PlayerPlane;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPlaneTest { // Package-private visibility

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initialize JavaFX Toolkit
    }

    @Test
    void testUserPlaneInitialization() {
        PlayerPlane playerPlane = new PlayerPlane(new ImageView(), 3);
        assertNotNull(playerPlane);
    }

    @Test
    void testInitialHealth() {
        PlayerPlane playerPlane = new PlayerPlane(new ImageView(), 3);
        assertEquals(3, playerPlane.getHealth());
    }



    @Test
    void testTakeDamage() {
        PlayerPlane playerPlane = new PlayerPlane(new ImageView(), 3);
        playerPlane.takeDamage();
        assertEquals(2, playerPlane.getHealth());
    }
}
