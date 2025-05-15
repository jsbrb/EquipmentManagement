package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.EquipmentAssignmentDTO;
import com.example.equipmentmanagement.model.*;
import com.example.equipmentmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class EquipmentAssignmentMapper {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private WorkService workService;

    @Autowired
    private WarehouseService warehouseService;

    public EquipmentAssignment toEntity(EquipmentAssignmentDTO dto) {
        EquipmentAssignment assignment = new EquipmentAssignment();
        assignment.setId(dto.getId());
        assignment.setAssignedAt(dto.getAssignedAt());
        assignment.setReturnedAt(dto.getReturnedAt());

        assignment.setEquipment(equipmentService.findById(dto.getEquipmentId()));
        assignment.setOperator(operatorService.findById(dto.getOperatorId()));
        assignment.setWork(workService.findById(dto.getWorkId()));
        assignment.setWarehouse(warehouseService.findById(dto.getWarehouseId()));

        return assignment;
    }

    public EquipmentAssignmentDTO toDTO(EquipmentAssignment assignment) {
        EquipmentAssignmentDTO dto = new EquipmentAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setAssignedAt(assignment.getAssignedAt());
        dto.setReturnedAt(assignment.getReturnedAt());

        // Formatear la fecha de asignación antes de asignarla al DTO
        if (assignment.getAssignedAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formattedAssignedAt = assignment.getAssignedAt().format(formatter);
            dto.setFormattedAssignedAt(formattedAssignedAt);  // Asignamos la fecha formateada al DTO
        }

        if (assignment.getEquipment() != null) {
            dto.setEquipmentId(assignment.getEquipment().getId());
            dto.setEquipmentName(assignment.getEquipment().getName());
        }

        if (assignment.getOperator() != null) {
            dto.setOperatorId(assignment.getOperator().getId());
            dto.setOperatorName(assignment.getOperator().getName());
        }

        if (assignment.getWork() != null) {
            dto.setWorkId(assignment.getWork().getId());
            dto.setWorkName(assignment.getWork().getName());
        }

        if (assignment.getWarehouse() != null) {
            dto.setWarehouseId(assignment.getWarehouse().getId());
            dto.setWarehouseName(assignment.getWarehouse().getName());
        }

        return dto;
    }

}
