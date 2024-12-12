package com.example.demo;

public class EventNotifier {
    private EventListener eventListener;

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
