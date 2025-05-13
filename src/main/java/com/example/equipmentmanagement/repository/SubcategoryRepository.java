package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository <Subcategory, Long> {
    Subcategory findByName(String name);
}
