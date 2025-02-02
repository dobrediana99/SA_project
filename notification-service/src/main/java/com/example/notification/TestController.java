package com.example.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final NotificationService notificationService;

    public TestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Endpoint to test the notification system
    @GetMapping("/test-notification")
    public String testNotification(@RequestParam String eventType, @RequestParam String message) {
        // Trigger the appropriate event
        if ("OrderCreated".equals(eventType)) {
            notificationService.notifyOrderCreated(message);
        } else if ("InventoryUpdated".equals(eventType)) {
            notificationService.notifyInventoryUpdated(message);
        }

        return "Notification sent for event: " + eventType;
    }
}
