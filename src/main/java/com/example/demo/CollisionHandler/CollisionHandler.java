package com.example.demo.CollisionHandler;

import com.example.demo.Entity.ActiveActorDestructible;
import com.example.demo.Entity.Player.PlayerPlane;
import com.example.demo.Entity.PowerUp.PowerUp;

import java.util.List;

/**
 * Singleton class for handling collision detection and resolution between game entities.
 *
 * @see ActiveActorDestructible
 * @see PowerUp
 * @see PlayerPlane
 */
public class CollisionHandler {

    // Single instance of CollisionHandler
    private static CollisionHandler instance;

    /**Private constructor to prevent direct instantiation
     */
    private CollisionHandler() {}

    /**
     *  Static method to get the single instance
      */

    public static CollisionHandler getInstance() {
        if (instance == null) {
            instance = new CollisionHandler();
        }
        return instance;
    }

    /**
     * Handles collisions between two groups of active destructible actors.
     *
     * @param actors1 the first group of actors
     * @param actors2 the second group of actors
     * {@link ActiveActorDestructible}
     */
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
    /**
     * Handles collisions between two groups of active destructible actors.
     *
     * @param user the User Plane
     * @param asteroids the Asteroids
     * {@link ActiveActorDestructible}
     */
    public void handleAsteroidCollisions(PlayerPlane user, List<ActiveActorDestructible> asteroids) {
        for (ActiveActorDestructible asteroid : asteroids) {
            if (asteroid.getBoundsInParent().intersects(user.getBoundsInParent())) {
                System.out.println("UserPlane collided with an asteroid!");
                user.takeDamage();
                asteroid.destroy();
            }
        }
    }

}
