package com.example.publisher.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_items") // Optional: specify the table name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {

    @Id
    private String id; // or use a different type (e.g., Long) if you prefer

    private int quantity;
}
