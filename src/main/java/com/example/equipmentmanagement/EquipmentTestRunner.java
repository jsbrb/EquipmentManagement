package com.example.equipmentmanagement;

import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EquipmentTestRunner implements CommandLineRunner {

    private final EquipmentRepository equipmentRepository;

    public EquipmentTestRunner(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==== Probando carga de equipos con subcategoría ====");
        List<Equipment> allEquipments = equipmentRepository.findAllWithSubcategories();

        for (Equipment e : allEquipments) {
            System.out.println("Equipo: " + e.getName() +
                    ", Subcategoría: " + (e.getSubcategory() != null ? e.getSubcategory().getName() : "NULL"));
        }
    }
}

