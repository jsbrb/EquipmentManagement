package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping
    public String listEquipment(Model model){
        model.addAttribute("equipmentList", equipmentRepository.findAll());
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
