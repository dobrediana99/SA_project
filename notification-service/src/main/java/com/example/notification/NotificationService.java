package com.example.notification;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final List<NotificationListener> listeners = new ArrayList<>();

    // Constructor-based dependency injection
    public NotificationService(List<NotificationListener> listeners) {
        this.listeners.addAll(listeners);
    }

    // Method to register listeners (if needed)
    public void registerListener(NotificationListener listener) {
        listeners.add(listener);
    }

    // Method to notify all listeners
    public void notify(String eventType, String message) {
        for (NotificationListener listener : listeners) {
            listener.onEvent(eventType, message);
        }
    }
}
