package com.example.publisher.service;

import com.example.publisher.dto.InventoryDTO;
import com.example.publisher.dto.InventoryFactory;
import com.example.publisher.model.InventoryItem;
import com.example.publisher.repository.InventoryRepository;
import com.example.publisher.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.publisher.utils.JsonUtil.objectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }


    public InventoryDTO addItem(InventoryDTO dto) {
        InventoryItem item = InventoryFactory.createInventoryDTO(dto);
        InventoryItem saveditem = inventoryRepository.save(item);
        kafkaTemplate.send("inventory-events", String.valueOf(item.getId()), JsonUtil.toJson(saveditem));
        return mapToDTO(saveditem);
    }

    public void adjustStockBasedOnOrder(String itemName, int quantityOrdered) {
        InventoryItem item = inventoryRepository.findByItemName(itemName)
                .orElseThrow(() -> new RuntimeException("Item not found: " + itemName));

        int updatedQuantity = item.getTotalAmount() - quantityOrdered;
        item.setTotalAmount(updatedQuantity);

        InventoryItem savedItem = inventoryRepository.save(item);

        log.info("Stock updated for item '{}': new quantity = {}", itemName, updatedQuantity);
        sendInventoryEvent(savedItem);
    }


    @KafkaListener(topics = "inventory-update-topic", groupId = "inventory-group")
    public void handleInventoryUpdate(String message) {
        try {
            InventoryDTO updateDTO = objectMapper.readValue(message, InventoryDTO.class);

            if (updateDTO.getItemName() == null) {
                throw new IllegalArgumentException("Received inventory update with null order ID");
            }

            adjustStockBasedOnOrder(updateDTO.getItemName(), updateDTO.getTotalAmount());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process inventory update message", e);
        }
    }


    private void sendInventoryEvent(InventoryItem item) {
        String message = JsonUtil.toJson(item);
        log.info("Publishing inventory event: {}", message);
        kafkaTemplate.send("inventory-events", String.valueOf(item.getId()), message);
    }
    public void deleteItem(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException("Item not found");
        }

        inventoryRepository.deleteById(id);
    }
    private InventoryDTO mapToDTO(InventoryItem order) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(order.getId());
        dto.setItemName(order.getItemName());
        dto.setTotalAmount(order.getTotalAmount());
        return dto;
    }
}
