package com.example.equipmentmanagement.controller;

import org.springframework.ui.Model;
import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.dto.WarehouseDTO;
import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.service.EquipmentAssignmentService;
import com.example.equipmentmanagement.service.WarehouseService;
import com.example.equipmentmanagement.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    @Autowired
    private WorkService workService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private EquipmentAssignmentService equipmentAssignmentService;

    @GetMapping("/work/map")
    public String showMap(Model model) {
        List<WorkDTO> works = workService.getAllWorks();
        List<Map<String, Object>> workMarkers = works.stream()
                .filter(w -> w.getLatitude() != null && w.getLongitude() != null)
                .map(w -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", w.getId());
                    map.put("name", w.getName());
                    map.put("lat", w.getLatitude());
                    map.put("lng", w.getLongitude());

                    List<EquipmentDTO> equiposEnUso = equipmentAssignmentService.getEquipmentInUseByWorkId(w.getId());
                    List<String> nombresEquipos = equiposEnUso.stream().map(EquipmentDTO::getName).toList();

                    map.put("equipments", nombresEquipos);
                    return map;
                }).toList();

        List<WarehouseDTO> warehouses = warehouseService.getAllWarehouses();
        List<Map<String, Object>> warehouseMarkers = warehouses.stream()
                .filter(w -> w.getLatitude() != null && w.getLongitude() != null)
                .map(w -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", w.getId());
                    map.put("name", w.getName());
                    map.put("lat", w.getLatitude());
                    map.put("lng", w.getLongitude());

                    List<EquipmentDTO> equiposDisponibles = warehouseService.getAvailableEquipmentByWarehouseId(w.getId());
                    List<String> nombresEquipos = equiposDisponibles.stream().map(EquipmentDTO::getName).toList();

                    map.put("equipments", nombresEquipos);
                    return map;
                }).toList();

        model.addAttribute("workMarkers", workMarkers);
        model.addAttribute("warehouseMarkers", warehouseMarkers);
        return "work/map";
    }
}
