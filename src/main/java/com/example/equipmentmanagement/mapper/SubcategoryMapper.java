package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.SubcategoryDTO;
import com.example.equipmentmanagement.model.Subcategory;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {

    public static SubcategoryDTO toDTO(Subcategory subcategory) {
        return new SubcategoryDTO(subcategory.getId(), subcategory.getName());
    }

    public static Subcategory toEntity(SubcategoryDTO dto) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(dto.getId());
        subcategory.setName(dto.getName());
        return subcategory;
    }
}
