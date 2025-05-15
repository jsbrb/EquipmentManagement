package com.example.equipmentmanagement.api;

import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
public class WorkApiController {

    @Autowired
    private WorkService workService;

    @GetMapping
    public List<WorkDTO> getAllWorks() {
        return workService.getAllWorks();
    }

    @GetMapping("/{id}")
    public WorkDTO getWorkById(@PathVariable Long id) {
        return workService.getWorkById(id);
    }

    @PostMapping
    public WorkDTO createWork(@RequestBody WorkDTO workDTO) {
        return workService.createdWork(workDTO);
    }

    @PutMapping("/{id}")
    public WorkDTO updateWork(@PathVariable Long id, @RequestBody WorkDTO workDTO) {
        return workService.updateWork(id, workDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteWork(@PathVariable Long id) {
        workService.deleteWork(id);
    }
}

