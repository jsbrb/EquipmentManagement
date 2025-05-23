package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.dto.WarehouseDTO;
import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*@GetMapping("/work/map")
    public String showMap(Model model) {
        List<WarehouseDTO> warehouses = warehouseService.getAllWarehouses();

        // Solo almacenes
        List<Map<String, Object>> warhouseMarkers = warehouses.stream()
                .filter(w -> w.getLatitude() != null && w.getLongitude() != null)
                .map(w -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", w.getId());
                    map.put("name", w.getName());
                    map.put("lat", w.getLatitude());
                    map.put("lng", w.getLongitude());

                    List<EquipmentDTO> almacenes = warehouseService.getAvailableEquipmentByWarehouseId(w.getId());
                    List<String> nombreAlmacenes = almacenes.stream()
                            .map(EquipmentDTO::getName)
                            .toList();

                    map.put("equipments", nombreAlmacenes);
                    return map;
                })
                .toList();

        model.addAttribute("warhouseMarkers", warhouseMarkers);
        return "work/map";
    }*/
}
