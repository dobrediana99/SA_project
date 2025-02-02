package com.example.publisher.controller;

import com.example.publisher.dto.InventoryDTO;
import com.example.publisher.model.InventoryItem;
import com.example.publisher.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class NavigationController {  // Renamed from NavigationController

    private final InventoryService inventoryService;

    // GET endpoint to list all inventory items
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getInventory() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @PostMapping
    public InventoryDTO addInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.addItem(inventoryDTO);
    }

    // PUT endpoint to update stock for a given item
    @PutMapping("/{itemId}")
    public ResponseEntity<InventoryItem> updateStock(@PathVariable Long itemId,
                                                     @RequestBody int newQuantity) {
        InventoryItem updatedItem = inventoryService.updateStock(itemId, newQuantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{itemId}")
    public void delInventory(@PathVariable Long itemId) {
        inventoryService.deleteItem(itemId);
    }
}
