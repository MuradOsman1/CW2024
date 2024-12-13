package com.example.demo.tests;

import com.example.demo.Entity.Boss.Boss;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class BossTest {

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initialize JavaFX Toolkit
    }

    private Boss boss;

    @BeforeEach
    void setUp() {
        boss = new Boss();
    }

    @Test
    void testInitialShieldState() {
        assertFalse(boss.getIsShielded());
    }

    @Test
    void testTakeDamageWithShield() throws Exception {
        // Access private activateShield method
        Method activateShield = Boss.class.getDeclaredMethod("activateShield");
        activateShield.setAccessible(true);
        activateShield.invoke(boss);

        boss.takeDamage();
        assertFalse(boss.isDestroyed());
    }

    @Test
    void testTakeDamageWithoutShield() {
        boss.takeDamage();
        assertTrue(boss.isDestroyed());
    }
}
