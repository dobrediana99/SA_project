package com.example.notification;

public class EmailNotificationListener implements NotificationListener {

    @Override
    public void onEvent(String eventType, String message) {
        if (eventType.equals("OrderCreated")) {
            // Logic for handling OrderCreated notification via email
            System.out.println("Sending email for OrderCreated event: " + message);
        } else if (eventType.equals("InventoryUpdated")) {
            // Logic for handling InventoryUpdated notification via email
            System.out.println("Sending email for InventoryUpdated event: " + message);
        }
    }
}
