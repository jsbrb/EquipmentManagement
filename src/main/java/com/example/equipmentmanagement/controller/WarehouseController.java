package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.WarehouseDTO;
import com.example.equipmentmanagement.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    // Listar todos los almacenes
    @GetMapping
    public String listWarehouses(Model model) {
        List<WarehouseDTO> warehouseList = warehouseService.getAllWarehouses();
        model.addAttribute("warehouseList", warehouseList);  // Asegúrate de que el nombre coincida con el de la vista
        model.addAttribute("activePage", "warehouse");

        return "warehouse/list";
    }

    // Mostrar formulario para crear un nuevo almacén
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("warehouse", new WarehouseDTO());
        model.addAttribute("activePage", "warehouse");
        return "warehouse/form";
    }

    // Mostrar formulario para editar un almacén existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        WarehouseDTO warehouseDTO = warehouseService.getWarehouseById(id);
        if (warehouseDTO == null) {
            model.addAttribute("error", "Almacén no encontrado.");
            return "redirect:/warehouse";
        }
        model.addAttribute("warehouse", warehouseDTO);
        model.addAttribute("activePage", "warehouse");
        return "warehouse/form";
    }

    // Guardar almacén (crear o actualizar)
    @PostMapping
    public String saveWarehouse(@ModelAttribute WarehouseDTO warehouseDTO, Model model) {
        // Agregar log para verificar los datos recibidos

        if (warehouseDTO.getName() == null || warehouseDTO.getName().isEmpty()) {
            model.addAttribute("error", "El nombre es obligatorio.");
            model.addAttribute("warehouse", warehouseDTO);
            return "warehouse/form";
        }

        // Agregar log para saber qué acción se está tomando (crear o actualizar)
        if (warehouseDTO.getId() != null) {
            warehouseService.updateWarehouse(warehouseDTO.getId(), warehouseDTO);
        } else {
            warehouseService.createWarehouse(warehouseDTO);
        }

        return "redirect:/warehouse";
    }


    // Eliminar almacén
    @PostMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable("id") Long id, Model model){
        try {
            warehouseService.deleteWarehouse(id);
        } catch (Exception e) {
            model.addAttribute("error", "Almacén no encontrado.");
        }
        return "redirect:/warehouse";
    }
}
