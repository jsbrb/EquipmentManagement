package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.equipmentmanagement.service.EquipmentService;

import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentRepository equipmentRepository;

    // Inyección del servicio EquipmentService
    @Autowired
    private EquipmentService equipmentService;

    // Listar todos los equipos
    @GetMapping
    public String listEquipments(Model model) {
        List<EquipmentDTO> equipmentList = equipmentRepository.findAll().stream()
                .map(equipmentService::toDTO)  // Convierte las entidades a DTOs
                .toList();
        model.addAttribute("equipmentList", equipmentList);
        return "equipment/list";
    }

    @GetMapping("/new")
    public String showForm (Model model){
        model.addAttribute("equipment", new Equipment());
        return "equipment/form";
    }

    @PostMapping
    public String saveEquipment(@ModelAttribute Equipment equipment){
        equipmentRepository.save(equipment);
        return "redirect:/equipment";
    }
}
