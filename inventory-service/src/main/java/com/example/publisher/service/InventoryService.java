package com.example.publisher.service;

import com.example.publisher.model.InventoryItem;
import com.example.publisher.repository.InventoryRepository;
import com.example.publisher.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Retrieve all inventory items from the database
    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    // Update the stock for an item and then publish an inventory event
    public InventoryItem updateStock(String itemId, int newQuantity) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElse(new InventoryItem(itemId, 0));
        item.setQuantity(newQuantity);
        InventoryItem savedItem = inventoryRepository.save(item);
        sendInventoryEvent(savedItem);
        return savedItem;
    }

    // Called by the Kafka consumer when an order event is received
    public void adjustStockBasedOnOrder(String itemId, int quantityOrdered) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElse(new InventoryItem(itemId, 0));
        int updatedQuantity = item.getQuantity() - quantityOrdered;
        item.setQuantity(updatedQuantity);
        InventoryItem savedItem = inventoryRepository.save(item);
        log.info("Stock updated for {}: new quantity = {}", itemId, updatedQuantity);
        sendInventoryEvent(savedItem);
    }

    // Publish an inventory update event to Kafka (e.g., to "inventory-events" topic)
    private void sendInventoryEvent(InventoryItem item) {
        String message = JsonUtil.toJson(item);
        log.info("Publishing inventory event: {}", message);
        kafkaTemplate.send("inventory-events", item.getId(), message);
    }
}
