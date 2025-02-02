package com.example.publisher.dto;

import com.example.publisher.model.InventoryItem;

public class InventoryFactory {
    public static InventoryItem createInventoryDTO(InventoryDTO inventoryDTO) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(inventoryDTO.getId());
        inventoryItem.setQuantity(inventoryDTO.getQuantity());
        return inventoryItem;
    }
}
