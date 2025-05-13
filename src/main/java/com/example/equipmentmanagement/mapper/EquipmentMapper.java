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
        List<SubcategoryDTO> subcategoryDTOs = equipment.getSubcategories()
                .stream()
                .map(SubcategoryMapper::toDTO)
                .collect(Collectors.toList());

        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getSerialNumber(),
                equipment.getCode(),
                subcategoryDTOs,
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

        // Obtener las subcategorías desde el DTO y convertirlas a entidad
        Set<Subcategory> subcategories = dto.getSubcategory()
                .stream()
                .map(SubcategoryMapper::toEntity)
                .collect(Collectors.toSet());

        equipment.setSubcategories(subcategories); // Ahora sí coincide con la firma

        return equipment;
    }
}

