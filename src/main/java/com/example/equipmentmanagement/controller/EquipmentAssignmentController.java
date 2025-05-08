package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.model.EquipmentAssignment;
import com.example.equipmentmanagement.repository.EquipmentAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipmentassignment")
public class EquipmentAssignmentController {
    @Autowired
    private EquipmentAssignmentRepository equipmentAssignmentRepository;

    @GetMapping
    public String listEquipmentAssignment(Model model){
        model.addAttribute("equipmentassignment", equipmentAssignmentRepository.findAll());
        return "equipmentassignment";
    }

    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("equipmentassigment", new EquipmentAssignment());
        return "equipmentassignment";
    }
}
