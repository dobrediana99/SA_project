package com.example.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void registerListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void notify(String eventType, String message) {
        for (NotificationListener listener : listeners) {
            listener.onEvent(eventType, message);
        }
    }
}
