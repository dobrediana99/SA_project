package com.example.notification;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final List<NotificationListener> listeners;

    // Constructor to allow registering listeners
    public NotificationService(List<NotificationListener> listeners) {
        this.listeners = listeners;
    }

    // Method to register listeners (if needed)
    public void registerListener(NotificationListener listener) {
        listeners.add(listener);
    }

    // Method to notify all listeners about an event
    public void notify(String eventType, String message) {
        for (NotificationListener listener : listeners) {
            listener.onEvent(eventType, message);
        }
    }

    // Specific method to notify OrderCreated event
    public void notifyOrderCreated(String message) {
        notify("OrderCreated", message);
    }

    // Specific method to notify InventoryUpdated event
    public void notifyInventoryUpdated(String message) {
        notify("InventoryUpdated", message);
    }
}
