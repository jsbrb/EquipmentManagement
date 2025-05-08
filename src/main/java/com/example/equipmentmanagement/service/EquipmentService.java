package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.OperatorRepository;
import com.example.equipmentmanagement.repository.WorkRepository;
import com.example.equipmentmanagement.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

    // Inyección de dependencias de repositorios
    private final OperatorRepository operatorRepository;
    private final WorkRepository workRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public EquipmentService(OperatorRepository operatorRepository, WorkRepository workRepository, WarehouseRepository warehouseRepository) {
        this.operatorRepository = operatorRepository;
        this.workRepository = workRepository;
        this.warehouseRepository = warehouseRepository;
    }

    // Método de conversión a DTO
    public EquipmentDTO toDTO(Equipment equipment) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(equipment.getId());
        dto.setName(equipment.getName());
        dto.setSerialNumber(equipment.getSerialNumber());
        dto.setBrand(equipment.getBrand());
        dto.setCurrentStatus(equipment.getCurrentStatus());
        if (equipment.getCurrentOperator() != null) {
            dto.setCurrentOperatorId(equipment.getCurrentOperator().getId());
        }
        if (equipment.getCurrentWork() != null) {
            dto.setCurrentWorkId(equipment.getCurrentWork().getId());
        }
        if (equipment.getWarehouse() != null) {
            dto.setWarehouseId(equipment.getWarehouse().getId());
        }
        return dto;
    }

    // Método de conversión de DTO a entidad
    public Equipment toEntity(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        equipment.setName(dto.getName());
        equipment.setSerialNumber(dto.getSerialNumber());
        equipment.setBrand(dto.getBrand());
        equipment.setCurrentStatus(dto.getCurrentStatus());

        // Cargar las entidades correspondientes por ID
        equipment.setCurrentOperator(operatorRepository.findById(dto.getCurrentOperatorId()).orElse(null));
        equipment.setCurrentWork(workRepository.findById(dto.getCurrentWorkId()).orElse(null));
        equipment.setWarehouse(warehouseRepository.findById(dto.getWarehouseId()).orElse(null));

        return equipment;
    }
}