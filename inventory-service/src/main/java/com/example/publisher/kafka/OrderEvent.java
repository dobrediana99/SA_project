package com.example.publisher.kafka;

import lombok.Data;

@Data
public class OrderEvent {
    private long id;
    private String customerName;
    private String itemName;
    private int totalAmount;
}
