package com.example.orderservice.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private Double totalAmount;
    private String status;
}