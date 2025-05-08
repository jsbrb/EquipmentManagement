package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.model.Work;
import com.example.equipmentmanagement.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkRepository workRepository;

    @GetMapping
    public String listWork(Model model){
        model.addAttribute("workList", workRepository.findAll());
        return "work/list";
    }

    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("work", new Work());
        return "work/form";
    }

    @PostMapping
    public String saveWork(@ModelAttribute Work work){
        workRepository.save(work);
        return "redirect:/work";
    }
}
