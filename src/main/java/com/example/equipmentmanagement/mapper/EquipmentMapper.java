package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.model.Equipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {

    // Convertir Equipment a EquipmentDTO
    public static EquipmentDTO toDTO(Equipment equipment) {
        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getSerialNumber(),
                equipment.getBrand(),
                equipment.getCurrentStatus()
        );
    }

    // Convertir EquipmentDTO a Equipment
    public Equipment toEntity(EquipmentDTO equipmentDTO) {
        Equipment equipment = new Equipment();
        equipment.setId(equipmentDTO.getId());
        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setBrand(equipmentDTO.getBrand());
        equipment.setCurrentStatus(equipmentDTO.getCurrentStatus());

        return equipment;
    }
}
