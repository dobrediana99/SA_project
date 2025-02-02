package com.example.publisher.service;

import com.example.publisher.dto.InventoryDTO;
import com.example.publisher.dto.InventoryFactory;
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
    private final KafkaTemplate<Long, String> kafkaTemplate;

    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    public InventoryItem updateStock(Long itemId, int newQuantity) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElse(new InventoryItem(itemId, 0));
        item.setQuantity(newQuantity);
        InventoryItem savedItem = inventoryRepository.save(item);
        sendInventoryEvent(savedItem);
        return savedItem;
    }

    public InventoryDTO addItem(InventoryDTO dto) {
        InventoryItem item = InventoryFactory.createInventoryDTO(dto);
        InventoryItem saveditem = inventoryRepository.save(item);
        kafkaTemplate.send("inventory-events", item.getId(), JsonUtil.toJson(saveditem));
        return mapToDTO(saveditem);
    }

    public void adjustStockBasedOnOrder(Long itemId, int quantityOrdered) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElse(new InventoryItem(itemId, 0));
        int updatedQuantity = item.getQuantity() - quantityOrdered;
        item.setQuantity(updatedQuantity);
        InventoryItem savedItem = inventoryRepository.save(item);
        log.info("Stock updated for {}: new quantity = {}", itemId, updatedQuantity);
        sendInventoryEvent(savedItem);
    }

    private void sendInventoryEvent(InventoryItem item) {
        String message = JsonUtil.toJson(item);
        log.info("Publishing inventory event: {}", message);
        kafkaTemplate.send("inventory-events", item.getId(), message);
    }
    public void deleteItem(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        inventoryRepository.deleteById(id);
    }
    private InventoryDTO mapToDTO(InventoryItem order) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(order.getId());
        dto.setQuantity(order.getQuantity());
        return dto;
    }
}
