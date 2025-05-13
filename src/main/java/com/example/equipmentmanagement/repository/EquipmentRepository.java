package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e LEFT JOIN FETCH e.subcategories")
    List<Equipment> findAllWithSubcategories();

}
