package com.example.notification;

public class SMSNotificationListener implements NotificationListener {
    @Override
    public void onEvent(String eventType, String message) {
        System.out.println("SMS sent for " + eventType + ": " + message);
    }
}
