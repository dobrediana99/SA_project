package com.example.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String customerName;
    private Double totalAmount;
}
