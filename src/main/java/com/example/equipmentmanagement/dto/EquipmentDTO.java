package com.example.equipmentmanagement.dto;

import com.example.equipmentmanagement.model.EquipmentStatus;

import java.util.List;

public class EquipmentDTO {

    private Long id;
    private String name;
    private String serialNumber;
    private String code;
    private List<SubcategoryDTO> subcategory;
    private EquipmentStatus currentStatus;

    // Constructor por defecto (sin parámetros)
    public EquipmentDTO() {
    }

    // Constructor
    public EquipmentDTO(Long id, String name, String serialNumber, String code, List<SubcategoryDTO> subcategory, EquipmentStatus currentStatus) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.code=code;
        this.subcategory = subcategory;
        this.currentStatus = currentStatus;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<SubcategoryDTO> getSubcategory() {   // Getter que devuelve una lista
        return subcategory;
    }

    public void setSubcategory(List<SubcategoryDTO> subcategory) {   // Setter que acepta una lista
        this.subcategory = subcategory;
    }

    public EquipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(EquipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
