package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentAssignmentDTO;
import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.model.EquipmentAssignment;
import com.example.equipmentmanagement.model.Warehouse;
import com.example.equipmentmanagement.repository.EquipmentAssignmentRepository;
import com.example.equipmentmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/equipmentAssignment")
public class EquipmentAssignmentController {

    @Autowired
    private EquipmentAssignmentService assignmentService;

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private WorkService workService;
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "equipmentAssignment/list"; // Nombre de la vista: assignments.html
    }


    @GetMapping("/new")
    public String newAssignmentForm(Model model) {

        // Verifica que los servicios devuelvan datos
        System.out.println("Equipos: " + equipmentService.getAllEquipments());
        System.out.println("Operadores: " + operatorService.getAllOperators());
        System.out.println("Obras: " + workService.getAllWorks());
        System.out.println("Almacenes: " + warehouseService.getAllWarehouses());


        model.addAttribute("assignment", new EquipmentAssignmentDTO());
        // ▶ Aquí: usar getAllEquipments (DTOs) en vez de entidades
        List<EquipmentDTO> equipmentDTOs = equipmentService.getAllEquipments();
        model.addAttribute("equipments", equipmentDTOs);
        //model.addAttribute("equipments", equipmentService.findAvailableEquipments());
        model.addAttribute("operators", operatorService.getAllOperators());
        model.addAttribute("works", workService.getAllWorks());
        return "equipmentAssignment/form";
    }

    @PostMapping
    public String saveAssignment(@ModelAttribute EquipmentAssignmentDTO assignmentDTO) {
        assignmentService.createAssignment(assignmentDTO);
        return "redirect:/equipmentAssignment";
    }

    @GetMapping("/{id}/return")
    public String returnForm(@PathVariable Long id, Model model) {
        EquipmentAssignmentDTO dto = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", dto);
        return "equipmentAssignment/return";
    }

    @PostMapping("/{id}/return")
    public String processReturn(@PathVariable Long id) {
        assignmentService.returnAssignment(id);
        return "redirect:/equipmentAssignment";
    }
}
