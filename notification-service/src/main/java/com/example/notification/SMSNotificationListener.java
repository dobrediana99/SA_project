package com.example.notification;

public class SMSNotificationListener implements NotificationListener {

    @Override
    public void onEvent(String eventType, String message) {
        if ("OrderCreated".equals(eventType)) {
            // Logic for handling OrderCreated notification via SMS
            System.out.println("Sending SMS for OrderCreated event: " + message);
        } else if ("InventoryUpdated".equals(eventType)) {
            // Logic for handling InventoryUpdated notification via SMS
            System.out.println("Sending SMS for InventoryUpdated event: " + message);
        } else {
            System.out.println("Unknown event type: " + eventType);
        }
    }
}
