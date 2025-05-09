package com.example.equipmentmanagement.dto;

import com.example.equipmentmanagement.model.EquipmentStatus;

public class EquipmentDTO {

    private Long id;
    private String name;
    private String serialNumber;
    private String brand;
    private EquipmentStatus currentStatus;

    // Constructor por defecto (sin parámetros)
    public EquipmentDTO() {
    }

    // Constructor
    public EquipmentDTO(Long id, String name, String serialNumber, String brand, EquipmentStatus currentStatus) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.brand = brand;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public EquipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(EquipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
