package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByName(String name);
}
