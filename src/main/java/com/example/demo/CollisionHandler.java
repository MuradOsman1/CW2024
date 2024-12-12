package com.example.demo;

import javafx.scene.Group;

import java.util.List;

public class CollisionHandler {

    // Single instance of CollisionHandler
    private static CollisionHandler instance;

    // Private constructor to prevent direct instantiation
    private CollisionHandler() {}

    // Static method to get the single instance
    public static CollisionHandler getInstance() {
        if (instance == null) {
            instance = new CollisionHandler();
        }
        return instance;
    }

    // Handle collisions between two lists of actors
    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }

    public void handleAsteroidCollisions(UserPlane user, List<ActiveActorDestructible> asteroids) {
        for (ActiveActorDestructible asteroid : asteroids) {
            if (asteroid.getBoundsInParent().intersects(user.getBoundsInParent())) {
                System.out.println("UserPlane collided with an asteroid!");
                user.takeDamage();
                asteroid.destroy();
            }
        }
    }

}
