package com.example.notification;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationServiceTest {
    @Test
    void testNotify() {
        NotificationService service = new NotificationService();
        NotificationListener listener = Mockito.mock(NotificationListener.class);

        service.registerListener(listener);
        service.notify("Test Event", "This is a test message");

        Mockito.verify(listener).onEvent("Test Event", "This is a test message");
    }
}
