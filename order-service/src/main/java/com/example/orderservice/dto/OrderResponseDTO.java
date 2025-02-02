package com.example.orderservice.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private String itemName;
    private int totalAmount;
    private String status;
}