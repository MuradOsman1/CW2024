package com.example.demo.EventHandler;

/**
 * Represents a game event that can occur during gameplay.
 * This class contains information about the type of event and any associated data.
 */
public class GameEvent {
    public enum EventType {
        LEVEL_COMPLETE,
        GAME_OVER,
        GAME_WIN
    }

    private final EventType eventType;
    private final String data;

    public GameEvent(EventType eventType) {
        this.eventType = eventType;
        this.data = "";
    }

    /**
     * Constructs a new GameEvent with the specified event type and associated data.
     *
     * @param eventType The type of the event, represented as one of the values in the EventType enum.
     * @param data Additional data related to the event, represented as a String.
     */
    public GameEvent(EventType eventType, String data) {
        this.eventType = eventType;
        this.data = data;
    }

    /**
     * Retrieves the type of the game event.
     *
     * @return the type of the event as defined by the {@link GameEvent.EventType} enumeration
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Retrieves the additional data related to the game event.
     *
     * @return the associated data of the game event as a String
     */
    public String getData() {
        return data;
    }
}
