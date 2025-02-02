package com.example.orderservice.kafka;

import com.example.orderservice.model.Order;
import com.example.publisher.dto.InventoryDTO;
import com.example.publisher.utils.JsonUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.publisher.utils.JsonUtil.objectMapper;

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
    public void publishInventoryUpdate(Long orderId, String itemName, int quantityOrdered) {
        try {
            InventoryDTO inventoryUpdate = new InventoryDTO();
            inventoryUpdate.setOrderId(orderId);
            inventoryUpdate.setItemName(itemName);
            inventoryUpdate.setTotalAmount(quantityOrdered);
            String message = objectMapper.writeValueAsString(inventoryUpdate);
            kafkaTemplate.send("inventory-update-topic", String.valueOf(orderId), message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize inventory update event", e);
        }
    }


}