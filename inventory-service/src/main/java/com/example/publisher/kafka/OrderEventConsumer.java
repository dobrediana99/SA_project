package com.example.publisher.kafka;

import com.example.publisher.service.InventoryService;
import com.example.publisher.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-events", groupId = "inventory-service-group")
    public void listenOrderEvents(String orderEventJson) {
        log.info("Received order event: {}", orderEventJson);
        try {
            OrderEvent orderEvent = JsonUtil.fromJson(orderEventJson, OrderEvent.class);
            if (orderEvent != null) {
                inventoryService.adjustStockBasedOnOrder(orderEvent.getItemName() ,orderEvent.getTotalAmount());
            } else {
                log.error("Failed to deserialize order event");
            }
        } catch (Exception e) {
            log.error("Error processing order event", e);
        }
    }
}
