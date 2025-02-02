package com.example.publisher.kafka;

import lombok.Data;

@Data
public class OrderEvent {
    private Long itemId;
    private int quantity;
}
