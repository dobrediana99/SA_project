package com.example.publisher.kafka;

import lombok.Data;

@Data
public class OrderEvent {
    private Long itemId;
    private String itemName;
    private int quantity;
}
