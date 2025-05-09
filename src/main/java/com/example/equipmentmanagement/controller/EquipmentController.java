package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    // Listar todos los equipos
    @GetMapping
    public String listEquipments(Model model) {
        List<EquipmentDTO> equipmentList = equipmentService.getAllEquipments();
        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("activePage", "equipment");
        return "equipment/list";
    }

    // Mostrar formulario para crear un nuevo equipo
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("equipment", new EquipmentDTO());
        model.addAttribute("activePage", "equipment");
        return "equipment/form";
    }

    // Mostrar formulario para editar un equipo existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        EquipmentDTO equipmentDTO = equipmentService.getEquipmentById(id);
        if (equipmentDTO == null) {
            model.addAttribute("error", "Equipo no encontrado.");
            return "redirect:/equipment";
        }
        model.addAttribute("equipment", equipmentDTO);
        model.addAttribute("activePage", "equipment");
        return "equipment/form";
    }

    @PostMapping
    public String saveEquipment(@ModelAttribute EquipmentDTO equipmentDTO, Model model) {
        if (equipmentDTO.getName() == null || equipmentDTO.getName().isEmpty()
                || equipmentDTO.getSerialNumber() == null || equipmentDTO.getSerialNumber().isEmpty()
                || equipmentDTO.getBrand() == null || equipmentDTO.getBrand().isEmpty()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("equipment", equipmentDTO);
            return "equipment/form";
        }

        if (equipmentDTO.getId() != null) {
            equipmentService.updateEquipment(equipmentDTO.getId(), equipmentDTO);
        } else {
            equipmentService.createEquipment(equipmentDTO);
        }

        return "redirect:/equipment";
    }


    // Eliminar un equipo
    @PostMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable("id") Long id, Model model) {
        try {
            equipmentService.deleteEquipment(id);
        } catch (Exception e) {
            model.addAttribute("error", "Equipo no encontrado.");
        }
        return "redirect:/equipment";
    }
}
