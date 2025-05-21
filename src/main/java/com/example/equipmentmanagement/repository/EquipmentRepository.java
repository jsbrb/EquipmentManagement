package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.model.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e LEFT JOIN FETCH e.subcategory")
    List<Equipment> findAllWithSubcategories();

    List<Equipment> findByCurrentStatus(EquipmentStatus status);

    @Query("SELECT e FROM Equipment e LEFT JOIN FETCH e.subcategory WHERE e.currentStatus = :status")
    List<Equipment> findByCurrentStatusWithSubcategory(@Param("status") EquipmentStatus status);


}
