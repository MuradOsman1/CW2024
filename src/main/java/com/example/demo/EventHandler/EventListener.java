package com.example.demo.EventHandler;

/**
 * Represents a listener that reacts to specific game events.
 * Classes implementing this interface should define logic in response to various
 * {@link GameEvent} types. These events can include conditions such as level completion,
 * game over, or game victory. The handling of these events allows for customized behavior
 * in the game.
 */
public interface EventListener {
    void handleEvent(GameEvent event);
}
