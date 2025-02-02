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

    // Test endpoint to trigger notifications
    @GetMapping("/test-notification")
    public String testNotification(@RequestParam String eventType, @RequestParam String message) {
        // Trigger the notification
        notificationService.notify(eventType, message);
        return "Notification sent for event: " + eventType;
    }
}
