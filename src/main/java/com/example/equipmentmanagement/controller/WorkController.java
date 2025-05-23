package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.model.EquipmentAssignment;
import com.example.equipmentmanagement.model.EquipmentStatus;
import com.example.equipmentmanagement.model.Work;
import com.example.equipmentmanagement.repository.EquipmentAssignmentRepository;
import com.example.equipmentmanagement.repository.WorkRepository;
import com.example.equipmentmanagement.service.EquipmentAssignmentService;
import com.example.equipmentmanagement.service.EquipmentService;
import com.example.equipmentmanagement.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @Autowired
    private EquipmentAssignmentService equipmentAssignmentService;

    @Autowired
    private EquipmentAssignmentRepository equipmentAssignmentRepository;

    //Listar todas las obras
    @GetMapping
    public String listWorks(Model model) {
        List<WorkDTO> workList = workService.getAllWorks();
        model.addAttribute("workList", workList);
        model.addAttribute("activatePage", "work");
        return "work/list";
    }

    //Mostrar formulario para crear una nueva obra
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("work", new WorkDTO());
        model.addAttribute("activatePage", "work");
        return "work/form";
    }

    //Mostrar formulario para editar una obra existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        WorkDTO workDTO=workService.getWorkById(id);
        if(workDTO == null){
            model.addAttribute("error", "Obra no encontrada");
            return "redirect:/work";
        }

        System.out.println("DEBUG - Location al cargar el formulario: " + workDTO.getLocation());

        model.addAttribute("work", workDTO);
        model.addAttribute("activePage", "work");
        return "work/form";
    }

    @PostMapping
    public String saveWork(@ModelAttribute WorkDTO workDTO, Model model){
        if(workDTO.getName() == null || workDTO.getName().isEmpty() ||
        workDTO.getLocation() == null || workDTO.getLocation().isEmpty()){
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("work", workDTO);
            return "work/form";
        }

        if(workDTO.getId() !=null){
            workService.updateWork(workDTO.getId(), workDTO);
        } else {
            workService.createdWork(workDTO);
        }
        return "redirect:/work";
    }

    //Eliminar una obra
    @PostMapping("/delete/{id}")
    public String deleteWork(@PathVariable("id") Long id, Model model){
        try {
            workService.deleteWork(id);
        } catch (Exception e){
            model.addAttribute("error", "Obra no encontrada.");
        }
        return "redirect:/work";
    }

    /*
    @GetMapping("/map")
    public String showMap(Model model) {
        List<WorkDTO> works = workService.getAllWorks();

        // Solo obras con ubicación válida y equipos en uso
        List<Map<String, Object>> workMarkers = works.stream()
                .filter(w -> w.getLatitude() != null && w.getLongitude() != null)
                .map(w -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", w.getId());
                    map.put("name", w.getName());
                    map.put("lat", w.getLatitude());
                    map.put("lng", w.getLongitude());

                    List<EquipmentDTO> equiposEnUso = equipmentAssignmentService.getEquipmentInUseByWorkId(w.getId());
                    List<String> nombresEquipos = equiposEnUso.stream()
                            .map(EquipmentDTO::getName)
                            .toList();

                    map.put("equipments", nombresEquipos);
                    return map;
                })
                .toList();

        model.addAttribute("workMarkers", workMarkers);
        return "work/map";
    }*/



}
