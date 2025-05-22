package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.model.Subcategory;
import com.example.equipmentmanagement.repository.WarehouseRepository;
import com.example.equipmentmanagement.service.EquipmentService;
import com.example.equipmentmanagement.service.SubcategoryService;
import com.example.equipmentmanagement.service.WarehouseService;
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

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private WarehouseService warehouseService;

    // Listar todos los equipos con filtros y ordenamientos
    @GetMapping
    public String listEquipments(@RequestParam(required = false) String search,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(defaultValue = "name") String sortField,
                                 @RequestParam(defaultValue = "ASC") String direction,
                                 Model model) {
        List<EquipmentDTO> filteredEquipments = equipmentService.filterAndSort(search, status, sortField, direction);

        model.addAttribute("equipments", filteredEquipments);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("sortField", sortField);
        model.addAttribute("direction", direction);

        for (EquipmentDTO e : filteredEquipments) {
            System.out.println("DTO Equipo: " + e.getName() + ", Subcategoría: " + e.getSubcategoryName());
        }

        return "equipment/list"; // Vista Thymeleaf correspondiente
    }

    // Mostrar formulario para crear un nuevo equipo
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        model.addAttribute("equipmentDTO", equipmentDTO);
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        model.addAttribute("activePage", "equipment");

        System.out.println("warehouseId en GET: " + equipmentDTO.getWarehouseId());

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
        model.addAttribute("equipmentDTO", equipmentDTO);
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        model.addAttribute("activePage", "equipment");

        System.out.println("warehouseId en GET: " + equipmentDTO.getWarehouseId());

        return "equipment/form";
    }

    @PostMapping
    public String saveEquipment(@ModelAttribute EquipmentDTO equipmentDTO, Model model) {
        if (equipmentDTO.getName() == null || equipmentDTO.getName().isEmpty()
                || equipmentDTO.getSerialNumber() == null || equipmentDTO.getSerialNumber().isEmpty() || equipmentDTO.getCode() == null || equipmentDTO.getCode().isEmpty()
                || equipmentDTO.getSubcategoryId() == null) {  // Validar lista no vacía
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("equipmentDTO", equipmentDTO);
            return "equipment/form";
        }

        if (equipmentDTO.getId() != null) {
            equipmentService.updateEquipment(equipmentDTO.getId(), equipmentDTO);
        } else {
            equipmentService.createEquipment(equipmentDTO);
        }

        System.out.println("warehouseId en POST error: " + equipmentDTO.getWarehouseId());

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
