package com.example.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.order-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderEvent(String message) {
        System.out.println("Received Order Event: " + message);
        sendNotification("Order Event", message);
    }

    @KafkaListener(topics = "${spring.kafka.topic.inventory-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleInventoryEvent(String message) {
        System.out.println("Received Inventory Event: " + message);
        sendNotification("Inventory Event", message);
    }

    private void sendNotification(String type, String message) {
        System.out.println("Sending " + type + " Notification: " + message);
    }
}
