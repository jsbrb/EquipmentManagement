package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.dto.SubcategoryDTO;
import com.example.equipmentmanagement.mapper.EquipmentMapper;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.model.EquipmentStatus;
import com.example.equipmentmanagement.model.Subcategory;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import com.example.equipmentmanagement.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    //Obtener todos los equipos
    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAllWithSubcategories();

        List<EquipmentDTO> equipmentDTOs = equipments.stream()
                .map(EquipmentMapper::toDTO)
                .toList();

        return equipments.stream().map(EquipmentMapper::toDTO).collect(Collectors.toList());
    }

    public EquipmentDTO getEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        Long subcategoryId = null;
        if (equipment.getSubcategory() != null) {
            subcategoryId = equipment.getSubcategory().getId();
        }

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

    // Crear un nuevo equipo
    public EquipmentDTO createEquipment(EquipmentDTO equipmentDTO) {
        if (equipmentDTO.getId() != null) {
            throw new RuntimeException("No se puede crear un equipo con un ID existente");
        }

        // Validar y buscar la subcategoría
        Long subcategoryId = equipmentDTO.getSubcategoryId();
        if (subcategoryId == null) {
            throw new IllegalArgumentException("El ID de la subcategoría no puede ser null");
        }

        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        // Crear y poblar el nuevo equipo
        Equipment equipment = new Equipment();
        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setCode(equipmentDTO.getCode());
        equipment.setSubcategory(subcategory);
        //equipment.setCurrentStatus(equipmentDTO.getCurrentStatus());

        // Guardar el equipo
        Equipment savedEquipment = equipmentRepository.save(equipment);

        // Convertir a DTO para la respuesta
        return new EquipmentDTO(
                savedEquipment.getId(),
                savedEquipment.getName(),
                savedEquipment.getSerialNumber(),
                savedEquipment.getCode(),
                subcategoryId,
                subcategory.getName(),
                savedEquipment.getCurrentStatus()
        );
    }

    // Actualizar un equipo
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        // Asignar las subcategorías
        Long subcategoryId = equipmentDTO.getSubcategoryId();
        if (subcategoryId == null) {
            throw new RuntimeException("El ID de la subcategoría no puede ser null");
        }

        // Buscar la subcategoría en la BD
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setCode(equipmentDTO.getCode());
        equipment.setSubcategory(subcategory);
        equipment.setCurrentStatus(equipmentDTO.getCurrentStatus());

        Equipment updatedEquipment = equipmentRepository.save(equipment);
        return new EquipmentDTO(
                updatedEquipment.getId(),
                updatedEquipment.getName(),
                updatedEquipment.getSerialNumber(),
                updatedEquipment.getCode(),
                subcategoryId,
                subcategory.getName(),
                updatedEquipment.getCurrentStatus()
        );
    }

    // Eliminar un equipo
    public void deleteEquipment(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        equipmentRepository.delete(equipment);
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + id));
    }

    //Función para listar todos los equipos que estén DISPONIBLES
    public List<Equipment> findAvailableEquipments() {
        return equipmentRepository.findByCurrentStatus(EquipmentStatus.DISPONIBLE);
    }

    public List<EquipmentDTO> filterAndSort(String search, String status, String sortField, String direction) {
        List<Equipment> equipments;

        if (status != null && !status.isEmpty()) {
            EquipmentStatus equipmentStatus = EquipmentStatus.valueOf(status.toUpperCase());
            equipments = equipmentRepository.findByCurrentStatusWithSubcategory(equipmentStatus);
        } else {
            equipments = equipmentRepository.findAllWithSubcategories();
        }

        // El resto queda igual
        if (search != null && !search.isEmpty()) {
            equipments = equipments.stream()
                    .filter(equipment -> equipment.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if ("name".equalsIgnoreCase(sortField)) {
            if ("ASC".equalsIgnoreCase(direction)) {
                equipments.sort(Comparator.comparing(Equipment::getName));
            } else {
                equipments.sort(Comparator.comparing(Equipment::getName).reversed());
            }
        } else if ("status".equalsIgnoreCase(sortField)) {
            if ("ASC".equalsIgnoreCase(direction)) {
                equipments.sort(Comparator.comparing(Equipment::getCurrentStatus));
            } else {
                equipments.sort(Comparator.comparing(Equipment::getCurrentStatus).reversed());
            }
        }

        for (Equipment e : equipments) {
            System.out.println("Equipo: " + e.getName() + ", Subcategoría: " + (e.getSubcategory() != null ? e.getSubcategory().getName() : "NULL"));
        }

        return equipments.stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}

