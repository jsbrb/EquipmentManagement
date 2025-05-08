package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.model.Operator;
import com.example.equipmentmanagement.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping
    public String listOperator(Model model){
        model.addAttribute("operatorList", operatorRepository.findAll());
        return "operator/list";
    }

    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("operator", new Operator());
        return "operator/form";
    }

    @PostMapping
    public String saveOperator(@ModelAttribute Operator operator){
        operatorRepository.save(operator);
        return "redirect:/operator";
    }

}
