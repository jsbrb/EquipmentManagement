package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    // Inyección de dependencias del repositorio
    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    // Obtener todos los equipos
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    // Obtener un equipo por su ID
    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    // Crear o actualizar un equipo
    public Equipment saveOrUpdateEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    // Eliminar un equipo por su ID
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }

    // Aquí podrías agregar más métodos si necesitas lógica específica, como:
    // - Asignar un equipo a un operador
    // - Filtrar equipos por estado
    // - Otras operaciones de negocio
}
