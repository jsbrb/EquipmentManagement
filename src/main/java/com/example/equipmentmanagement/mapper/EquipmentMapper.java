package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.dto.SubcategoryDTO;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.model.Subcategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EquipmentMapper {

    // Convertir Equipment a EquipmentDTO
    public static EquipmentDTO toDTO(Equipment equipment) {
        Long subcategoryId = equipment.getSubcategory() != null ? equipment.getSubcategory().getId() : null;
        String subcategoryName = equipment.getSubcategory() != null ? equipment.getSubcategory().getName() : null;


        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getSerialNumber(),
                equipment.getCode(),
                subcategoryId,
                subcategoryName,
                equipment.getCurrentStatus()
        );
    }

    // Convertir EquipmentDTO a Equipment
    public static Equipment toEntity(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        equipment.setName(dto.getName());
        equipment.setSerialNumber(dto.getSerialNumber());
        equipment.setCode(dto.getCode());
        equipment.setCurrentStatus(dto.getCurrentStatus());

        if (dto.getSubcategoryId() != null) {
            Subcategory subcategory = new Subcategory();
            subcategory.setId(dto.getSubcategoryId());
            // Si tienes más campos en DTO para Subcategory, asignarlos aquí
            equipment.setSubcategory(subcategory);
        }

        return equipment;
    }
}

