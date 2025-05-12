package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.OperatorDTO;
import com.example.equipmentmanagement.model.Operator;
import com.example.equipmentmanagement.repository.OperatorRepository;
import com.example.equipmentmanagement.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @GetMapping
    public String listOperators(Model model) {
        List<OperatorDTO> operatorList = operatorService.getAllOperators();
        model.addAttribute("operatorList", operatorList);
        model.addAttribute("activePage", "operartor");
        return "operator/list";
    }

    // Mostrar formulario para crear un nuevo operario
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("operator", new OperatorDTO());
        model.addAttribute("activePage", "operator");
        return "operator/form";
    }

    // Mostrar formulario para editar un operario existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        OperatorDTO operatorDTO = operatorService.getOperatorById(id);
        if (operatorDTO == null) {
            model.addAttribute("error", "Operario no encontrado.");
            return "redirect:/operator";
        }
        model.addAttribute("operator", operatorDTO);
        model.addAttribute("activePage", "operator");
        return "operator/form";
    }

    // Guardar operario (crear o actualizar)
    @PostMapping
    public String saveOperator(@ModelAttribute OperatorDTO operatorDTO, Model model){
        if (operatorDTO.getName() == null || operatorDTO.getName().isEmpty()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("operator", operatorDTO);
            return "operator/form";
        }

        if (operatorDTO.getId() != null) {
            operatorService.updateOperator(operatorDTO.getId(), operatorDTO);
        } else {
            operatorService.createOperator(operatorDTO);
        }

        return "redirect:/operator";
    }

    // Eliminar operario
    @PostMapping("/delete/{id}")
    public String deleteOperator(@PathVariable("id") Long id, Model model) {
        try {
            operatorService.deleteOperator(id);
        } catch (Exception e) {
            model.addAttribute("error", "Operario no encontrado.");
        }
        return "redirect:/operator";
    }
}
