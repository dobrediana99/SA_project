package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.model.Order;

public class OrderFactory {
    public static Order createOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus("PENDING");
        return order;
    }
}