package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.EquipmentAssignmentDTO;
import com.example.equipmentmanagement.model.*;
import com.example.equipmentmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        Equipment equipment = assignment.getEquipment();
        if (equipment != null) {
            dto.setEquipmentId(equipment.getId());
            dto.setEquipmentName(equipment.getName());
        }

        Operator operator = assignment.getOperator();
        if (operator != null) {
            dto.setOperatorId(operator.getId());
            dto.setOperatorName(operator.getName());
        }

        Work work = assignment.getWork();
        if (work != null) {
            dto.setWorkId(work.getId());
        }

        Warehouse warehouse = assignment.getWarehouse();
        if (warehouse != null) {
            dto.setWarehouseId(warehouse.getId());
            dto.setWarehouseName(warehouse.getName());
        }

        return dto;
    }
}
