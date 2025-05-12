package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.model.Work;
import com.example.equipmentmanagement.repository.WorkRepository;
import com.example.equipmentmanagement.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

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

}
