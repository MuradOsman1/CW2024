package com.example.demo;

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

    public GameEvent(EventType eventType, String data) {
        this.eventType = eventType;
        this.data = data;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getData() {
        return data;
    }
}
