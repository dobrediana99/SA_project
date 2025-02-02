package com.example.publisher.controller;

import com.example.publisher.dto.InventoryDTO;
import com.example.publisher.model.InventoryItem;
import com.example.publisher.repository.InventoryRepository;
import com.example.publisher.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class NavigationController {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getInventory() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @PostMapping
    public InventoryDTO addInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.addItem(inventoryDTO);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<InventoryItem> updateStock(@PathVariable Long itemId,@RequestBody InventoryDTO inventoryDTO) {
        InventoryItem item = inventoryRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("itemId:" + itemId));
        item.setTotalAmount(inventoryDTO.getTotalAmount());
        item.setItemName(inventoryDTO.getItemName());
        item.setId(inventoryDTO.getId());
        InventoryItem savedItem = inventoryRepository.save(item);
        return ResponseEntity.ok(savedItem);
    }

    @DeleteMapping("/{itemId}")
    public void delInventory(@PathVariable Long itemId) {
        inventoryService.deleteItem(itemId);
    }
}
