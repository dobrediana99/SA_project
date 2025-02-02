package com.example.notification;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class NotificationServiceTest {

    @Test
    void testNotify() {
        // Create a mock NotificationListener
        NotificationListener listener = Mockito.mock(NotificationListener.class);
        
        // Create NotificationService and register the mocked listener
        NotificationService service = new NotificationService(List.of(listener));

        // Trigger the notify method
        service.notify("Test Event", "This is a test message");

        // Verify that the listener's onEvent method was called with the correct parameters
        Mockito.verify(listener).onEvent("Test Event", "This is a test message");
    }
}
