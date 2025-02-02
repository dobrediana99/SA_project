package com.example.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private final List<NotificationListener> listeners = new ArrayList<>();

    // Constructor to allow registering listeners
    public NotificationService(List<NotificationListener> listeners) {
        this.listeners.addAll(listeners);
    }

    // Method to register listeners (if needed)
    public void registerListener(NotificationListener listener) {
        listeners.add(listener);
    }

    // Notify all listeners about an event
    public void notify(String eventType, String message) {
        for (NotificationListener listener : listeners) {
            listener.onEvent(eventType, message);
        }
    }
}
