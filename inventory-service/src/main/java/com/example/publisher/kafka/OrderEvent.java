package com.example.publisher.kafka;

import lombok.Data;

@Data
public class OrderEvent {
    private String itemId;
    private int quantity;
}
