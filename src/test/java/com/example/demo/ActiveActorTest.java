//package com.example.demo;
//
//import javafx.scene.image.ImageView;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ActiveActorTest {
//
//    private TestActiveActor activeActor;
//
//    // Concrete subclass of ActiveActor for testing
//    static class TestActiveActor extends ActiveActor {
//        public TestActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
//            super(imageName, imageHeight, initialXPos, initialYPos);
//        }
//
//        @Override
//        public void updatePosition() {
//            // No-op for testing
//        }
//    }
//
//    @BeforeEach
//    void setUp() {
//        // Initialize ActiveActor with test parameters
//        activeActor = new TestActiveActor("test.png", 100, 50.0, 50.0);
//    }
//
//    @Test
//    void testConstructorInitialization() {
//        // Check layout position
//        assertEquals(50.0, activeActor.getLayoutX(), "Initial X position should match the given value.");
//        assertEquals(50.0, activeActor.getLayoutY(), "Initial Y position should match the given value.");
//
//        // Check image properties
//        assertEquals(100.0, activeActor.getFitHeight(), "Image height should match the given value.");
//        assertTrue(activeActor.isPreserveRatio(), "Image ratio preservation should be enabled.");
//
//        // Ensure image is loaded (replace test.png with a real file for actual execution)
//        assertNotNull(activeActor.getImage(), "Image should be successfully loaded.");
//    }
//
//    @Test
//    void testMoveHorizontally() {
//        double initialX = activeActor.getTranslateX();
//
//        // Move right
//        activeActor.moveHorizontally(10.0);
//        assertEquals(initialX + 10.0, activeActor.getTranslateX(), "Actor should move right by 10.0 units.");
//
//        // Move left
//        activeActor.moveHorizontally(-5.0);
//        assertEquals(initialX + 5.0, activeActor.getTranslateX(), "Actor should move left by 5.0 units.");
//    }
//
//    @Test
//    void testMoveVertically() {
//        double initialY = activeActor.getTranslateY();
//
//        // Move down
//        activeActor.moveVertically(15.0);
//        assertEquals(initialY + 15.0, activeActor.getTranslateY(), "Actor should move down by 15.0 units.");
//
//        // Move up
//        activeActor.moveVertically(-10.0);
//        assertEquals(initialY + 5.0, activeActor.getTranslateY(), "Actor should move up by 10.0 units.");
//    }
//}
