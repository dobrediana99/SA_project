package com.example.orderservice.kafka;

import com.example.orderservice.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(Order order) {
        String message = String.format("Order created: %s", order);
        kafkaTemplate.send("order-events", message);
    }
}