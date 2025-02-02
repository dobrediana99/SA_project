package com.example.publisher.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class InventoryDTO {
    @Id
    private Long id;
    private Long orderId;
    private String itemName;
    private int totalAmount;
}
