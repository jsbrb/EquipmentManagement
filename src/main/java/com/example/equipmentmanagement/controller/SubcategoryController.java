package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.SubcategoryDTO;
import com.example.equipmentmanagement.model.Subcategory;
import com.example.equipmentmanagement.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryService service;

    @GetMapping
    public List<SubcategoryDTO> listAll() {
        return service.getAllSubcategories();
    }

    @PostMapping
    public SubcategoryDTO create(@RequestBody SubcategoryDTO subcategoryDTO) {
        return service.save(subcategoryDTO);
    }

    @GetMapping("/{id}")
    public SubcategoryDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
