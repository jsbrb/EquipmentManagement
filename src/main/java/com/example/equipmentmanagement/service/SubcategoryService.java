package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.SubcategoryDTO;
import com.example.equipmentmanagement.mapper.SubcategoryMapper;
import com.example.equipmentmanagement.model.Subcategory;
import com.example.equipmentmanagement.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository repository;

    public List<Subcategory> findAll() {
        return repository.findAll();
    }

    public List<SubcategoryDTO> getAllSubcategories() {
        return repository.findAll()
                .stream()
                .map(SubcategoryMapper::toDTO)
                .toList();
    }

    public SubcategoryDTO getById(Long id) {
        return repository.findById(id)
                .map(SubcategoryMapper::toDTO)
                .orElse(null);
    }

    public SubcategoryDTO save(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = SubcategoryMapper.toEntity(subcategoryDTO);
        Subcategory saved = repository.save(subcategory);
        return SubcategoryMapper.toDTO(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
