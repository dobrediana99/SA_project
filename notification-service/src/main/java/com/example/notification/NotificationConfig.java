package com.example.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    public EmailNotificationListener emailNotificationListener() {
        return new EmailNotificationListener();
    }

    @Bean
    public SMSNotificationListener smsNotificationListener() {
        return new SMSNotificationListener();
    }
}
