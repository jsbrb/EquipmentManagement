package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.mapper.EquipmentMapper;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    // Obtener todos los equipos
    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return equipments.stream().map(EquipmentMapper::toDTO).collect(Collectors.toList());
    }

    // Obtener un equipo por ID
    public EquipmentDTO getEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found"));
        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getSerialNumber(),
                equipment.getBrand(),
                equipment.getCurrentStatus()
        );
    }

    // Crear un nuevo equipo
    public EquipmentDTO createEquipment(EquipmentDTO equipmentDTO) {
        if (equipmentDTO.getId() != null) {
            throw new RuntimeException("No se puede crear un equipo con un ID existente");
        }

        Equipment equipment = new Equipment();
        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setBrand(equipmentDTO.getBrand());
        equipment.setCurrentStatus(equipmentDTO.getCurrentStatus());

        Equipment savedEquipment = equipmentRepository.save(equipment);
        return new EquipmentDTO(
                savedEquipment.getId(),
                savedEquipment.getName(),
                savedEquipment.getSerialNumber(),
                savedEquipment.getBrand(),
                savedEquipment.getCurrentStatus()
        );
    }

    // Actualizar un equipo
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        // Solo actualizamos los valores que vienen en el DTO
        equipment.setName(equipmentDTO.getName());
        equipment.setSerialNumber(equipmentDTO.getSerialNumber());
        equipment.setBrand(equipmentDTO.getBrand());

        Equipment updatedEquipment = equipmentRepository.save(equipment);  // Guardamos el equipo actualizado
        return new EquipmentDTO(
                updatedEquipment.getId(),
                updatedEquipment.getName(),
                updatedEquipment.getSerialNumber(),
                updatedEquipment.getBrand(),
                updatedEquipment.getCurrentStatus()
        );
    }

    // Eliminar un equipo
    public void deleteEquipment(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipmentRepository.delete(equipment);
    }
}
