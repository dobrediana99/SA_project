package com.example.notification;

public class EmailNotificationListener implements NotificationListener {
    @Override
    public void onEvent(String eventType, String message) {
        System.out.println("Email sent for " + eventType + ": " + message);
    }
}
