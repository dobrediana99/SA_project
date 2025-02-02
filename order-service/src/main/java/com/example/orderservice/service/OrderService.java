package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.dto.OrderResponseDTO;
import com.example.orderservice.kafka.OrderEventProducer;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderEventProducer kafkaProducer;

    public OrderService(OrderRepository repository, OrderEventProducer kafkaProducer) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = OrderFactory.createOrder(dto);
        Order savedOrder = repository.save(order);
        kafkaProducer.publishOrderEvent(savedOrder);
        return mapToDTO(savedOrder);
    }

    public OrderResponseDTO getOrder(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        Order updatedOrder = repository.save(order);

        kafkaProducer.publishOrderEvent(updatedOrder);

        if ("COMPLETED".equalsIgnoreCase(status)) {
            kafkaProducer.publishInventoryUpdate(order.getId(), order.getItemName(),  order.getTotalAmount());
        }

        return mapToDTO(updatedOrder);
    }


    public void completeOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        repository.deleteById(id);
    }

    private OrderResponseDTO mapToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setItemName(order.getItemName());
        dto.setCustomerName(order.getCustomerName());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
