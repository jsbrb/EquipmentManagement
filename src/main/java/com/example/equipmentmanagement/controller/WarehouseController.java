package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.model.Warehouse;
import com.example.equipmentmanagement.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping
    public String listWarehouse (Model model){
        model.addAttribute("warehouse", warehouseRepository.findAll());
        return "warehouse/list";
    }

    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("warehouse",new Warehouse());
        return "warehouse/form";
    }

    @PostMapping
    public String saveWarehouse(@ModelAttribute Warehouse warehouse){
        warehouseRepository.save(warehouse);
        return "redirect:/warehouse";
    }
}
