package com.example.equipmentmanagement.dto;

import com.example.equipmentmanagement.model.EquipmentStatus;

import java.util.List;

public class EquipmentDTO {

    private Long id;
    private String name;
    private String serialNumber;
    private String code;
    private Long subcategoryId;
    private String subcategoryName;
    private EquipmentStatus currentStatus;

    // Constructor por defecto (sin parámetros)
    public EquipmentDTO() {
    }

    // Constructor con id y nombre
    public EquipmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor
    public EquipmentDTO(Long id, String name, String serialNumber, String code, Long subcategoryId, String subcategoryName, EquipmentStatus currentStatus) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.code=code;
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
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

    public Long getSubcategoryId() {   // Getter que devuelve una lista
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {   // Setter que acepta una lista
        this.subcategoryId = subcategoryId;
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

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }
}
