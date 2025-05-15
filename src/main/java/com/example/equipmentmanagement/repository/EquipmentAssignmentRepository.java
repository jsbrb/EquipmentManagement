package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.model.EquipmentAssignment;
import com.example.equipmentmanagement.model.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentAssignmentRepository extends JpaRepository<EquipmentAssignment, Long> {
    List<EquipmentAssignment> findByWorkId(Long workId);
}
