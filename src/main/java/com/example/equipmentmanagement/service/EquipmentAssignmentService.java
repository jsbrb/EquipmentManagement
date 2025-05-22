package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.EquipmentAssignmentDTO;
import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.mapper.EquipmentAssignmentMapper;
import com.example.equipmentmanagement.mapper.EquipmentMapper;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.model.EquipmentAssignment;
import com.example.equipmentmanagement.model.EquipmentStatus;
import com.example.equipmentmanagement.repository.EquipmentAssignmentRepository;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import com.example.equipmentmanagement.repository.OperatorRepository;
import com.example.equipmentmanagement.repository.WarehouseRepository;
import com.example.equipmentmanagement.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.equipmentmanagement.mapper.EquipmentMapper.toDTO;

@Service
public class EquipmentAssignmentService {

    @Autowired
    private EquipmentAssignmentRepository assignmentRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private EquipmentAssignmentMapper mapper;

    // Crear un formateador con el formato que estás utilizando
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Obtener todas las asignaciones
    public List<EquipmentAssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(mapper::toDTO)
                .sorted(Comparator.comparing(EquipmentAssignmentDTO::getAssignedAt).reversed())
                .collect(Collectors.toList());
    }

    // Método para obtener una asignación por ID
    public EquipmentAssignmentDTO getAssignmentById(Long id) {
        EquipmentAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        return mapper.toDTO(assignment);
    }

    // Crear una asignación
    public EquipmentAssignmentDTO createAssignment(EquipmentAssignmentDTO dto) {
        // 1. Obtiene el equipo y comprueba que está libre
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        if (equipment.getCurrentStatus() == EquipmentStatus.EN_USO) {
            throw new RuntimeException("Este equipo ya está en uso.");
        }

        // 2. Construye la entidad de asignación
        EquipmentAssignment assignment = new EquipmentAssignment();
        assignment.setEquipment(equipment);
        assignment.setOperator(operatorRepository.findById(dto.getOperatorId())
                .orElseThrow(() -> new RuntimeException("Operario no encontrado")));
        assignment.setWork(workRepository.findById(dto.getWorkId())
                .orElseThrow(() -> new RuntimeException("Trabajo no encontrado")));
        // ← Asigna el warehouse que ya viene en el equipo
        assignment.setWarehouse(equipment.getWarehouse());

        // 3.  Asignar la fecha actual
        LocalDateTime assignedAt = LocalDateTime.now();
        assignment.setAssignedAt(assignedAt);

        // 4. Formatear la fecha antes de asignarla al DTO
        String formattedAssignedAt = assignedAt.format(formatter);  // Formateamos la fecha
        dto.setFormattedAssignedAt(formattedAssignedAt);  // Asignamos la fecha formateada al DTO

        // 5. Cambiar estado del equipo a EN_USO
        equipment.setCurrentStatus(EquipmentStatus.EN_USO);
        equipment.setCurrentOperator(assignment.getOperator());
        equipment.setCurrentWork(assignment.getWork());

        assignmentRepository.save(assignment);
        equipmentRepository.save(equipment);

        System.out.println(assignment.getWarehouse().getName());

        return dto; // Devolver el DTO con la fecha formateada
    }


    // Registrar devolución del equipo
    public EquipmentAssignmentDTO returnAssignment(Long assignmentId) {
        EquipmentAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        if (assignment.getReturnedAt() != null) {
            throw new RuntimeException("Este equipo ya ha sido devuelto.");
        }

        assignment.setReturnedAt(LocalDateTime.now());

        // Cambiar estado del equipo a DISPONIBLE
        Equipment equipment = assignment.getEquipment();
        equipment.setCurrentStatus(EquipmentStatus.DISPONIBLE);
        equipment.setCurrentOperator(null);
        equipment.setCurrentWork(null);

        assignmentRepository.save(assignment);
        equipmentRepository.save(equipment);

        return mapper.toDTO(assignment);
    }

    // Método para obtener equipos EN_USO agrupados por obra
    public List<EquipmentDTO> getEquipmentInUseByWorkId(Long workId) {
        return assignmentRepository.findByWorkId(workId).stream()
                .filter(a -> a.getEquipment() != null && EquipmentStatus.EN_USO.equals(a.getEquipment().getCurrentStatus()))
                .map(a -> new EquipmentDTO(a.getEquipment().getId(), a.getEquipment().getName()))
                .toList();
    }

}

