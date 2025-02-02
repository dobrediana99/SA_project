package com.example.publisher.repository;

import com.example.publisher.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {
    // You can add custom query methods here if needed
}
