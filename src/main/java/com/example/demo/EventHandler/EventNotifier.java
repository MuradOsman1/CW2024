package com.example.demo.EventHandler;

/**
 * Provides a mechanism for notifying listeners about game events.
 * This class allows for the registration and notification of {@link EventListener} instances.
 */
public class EventNotifier {
    private EventListener eventListener;

    /**
     * Gets the currently registered {@link EventListener}.
     *
     * @return the currently registered event listener, or null if none is registered
     */
    public EventListener getEventListener() {
        return eventListener;
    }

    /**
     * Sets the {@link EventListener} to be notified of game events.
     *
     * @param eventListener the event listener to register
     */
    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
