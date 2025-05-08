package com.example.equipmentmanagement.dto;

public class EquipmentDTO {
    private Long id;
    private String name;
    private String serialNumber;
    private String brand;
    private String currentStatus;
    private Long currentOperatorId;
    private Long currentWorkId;
    private Long warehouseId;

    //Getter y Setters


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

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Long getCurrentOperatorId() {
        return currentOperatorId;
    }

    public void setCurrentOperatorId(Long currentOperatorId) {
        this.currentOperatorId = currentOperatorId;
    }

    public Long getCurrentWorkId() {
        return currentWorkId;
    }

    public void setCurrentWorkId(Long currentWorkId) {
        this.currentWorkId = currentWorkId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
