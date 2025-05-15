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

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
        return equipments.stream().map(EquipmentMapper::toDTO).collect(Collectors.toList());
    }

    // Obtener un equipo por ID
    public EquipmentDTO getEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getSerialNumber(),
                equipment.getCode(),
                equipment.getSubcategories().stream().map(sub -> new SubcategoryDTO(sub.getId(), sub.getName())).collect(Collectors.toList()), // Convertir a DTO
                equipment.getCurrentStatus()
        );
    }

    // Crear un nuevo equipo
    public EquipmentDTO createEquipment(EquipmentDTO equipmentDTO) {
        if (equipmentDTO.getId() != null) {
            throw new RuntimeException("No se puede crear un equipo con un ID existente");
        }

        // Asignar las subcategorías
        List<Subcategory> subcategories = equipmentDTO.getSubcategory().stream()
                .map(subDTO -> subcategoryRepository.findById(subDTO.getId()).orElseThrow(() -> new RuntimeException("Subcategoría no encontrada")))
                .collect(Collectors.toList());

        Equipment equipment = new Equipment();
        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setCode(equipmentDTO.getCode());
        equipment.setSubcategories(new HashSet<>(subcategories)); // Asignar subcategorías
        equipment.setCurrentStatus(equipmentDTO.getCurrentStatus());

        Equipment savedEquipment = equipmentRepository.save(equipment);
        return new EquipmentDTO(
                savedEquipment.getId(),
                savedEquipment.getName(),
                savedEquipment.getSerialNumber(),
                savedEquipment.getCode(),
                savedEquipment.getSubcategories().stream().map(sub -> new SubcategoryDTO(sub.getId(), sub.getName())).collect(Collectors.toList()), // Convertir a DTO
                savedEquipment.getCurrentStatus()
        );
    }

    // Actualizar un equipo
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        // Asignar las subcategorías
        List<Subcategory> subcategories = equipmentDTO.getSubcategory().stream()
                .map(subDTO -> subcategoryRepository.findById(subDTO.getId()).orElseThrow(() -> new RuntimeException("Subcategoría no encontrada")))
                .collect(Collectors.toList());

        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setCode(equipmentDTO.getCode());
        equipment.setSubcategories(new HashSet<>(subcategories)); // Asignar subcategorías
        equipment.setCurrentStatus(equipmentDTO.getCurrentStatus());

        Equipment updatedEquipment = equipmentRepository.save(equipment);
        return new EquipmentDTO(
                updatedEquipment.getId(),
                updatedEquipment.getName(),
                updatedEquipment.getSerialNumber(),
                updatedEquipment.getCode(),
                updatedEquipment.getSubcategories().stream().map(sub -> new SubcategoryDTO(sub.getId(), sub.getName())).collect(Collectors.toList()), // Convertir a DTO
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

        // Si hay un filtro de estado
        if (status != null && !status.isEmpty()) {
            EquipmentStatus equipmentStatus = EquipmentStatus.valueOf(status.toUpperCase());
            equipments = equipmentRepository.findByCurrentStatus(equipmentStatus);
        } else {
            equipments = equipmentRepository.findAllWithSubcategories();
        }

        // Si hay un filtro de búsqueda por nombre
        if (search != null && !search.isEmpty()) {
            equipments = equipments.stream()
                    .filter(equipment -> equipment.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Ordenar
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

        return equipments.stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

}

