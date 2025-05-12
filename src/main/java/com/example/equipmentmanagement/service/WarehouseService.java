package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.WarehouseDTO;
import com.example.equipmentmanagement.mapper.WarehouseMapper;
import com.example.equipmentmanagement.model.Warehouse;
import com.example.equipmentmanagement.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    //Obtener todos los almacenes
    public List<WarehouseDTO> getAllWarehouses(){
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream().map(WarehouseMapper::toDTO).collect(Collectors.toList());
    }

    //Obtener almacén por ID
    public WarehouseDTO getWarehouseById(Long id){
        Warehouse warehouse= warehouseRepository.findById(id).orElseThrow(()-> new RuntimeException("Warehouse not found"));
        return new WarehouseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation()
        );
    }

    //Crear un nuevo almacén
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        if (warehouseDTO.getId() != null) {
            throw new RuntimeException("No se puede crear un almacén con un ID exitente");
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseDTO.getName());
        warehouse.setLocation(warehouseDTO.getLocation());

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        System.out.println("Almacén guardado con ID: " + savedWarehouse.getId());
        return new WarehouseDTO(
                savedWarehouse.getId(),
                savedWarehouse.getName(),
                savedWarehouse.getLocation()
        );
    }
        //Actualizar un almacén
        public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO){
            Warehouse warehouse= warehouseRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Almacén no encontrado"));

            //Solo actualizamos los valores que vienen en el DTO
            warehouse.setName(warehouseDTO.getName());
            warehouse.setLocation(warehouseDTO.getLocation());

            Warehouse updateWarehouse = warehouseRepository.save(warehouse);
            return new WarehouseDTO(
                    updateWarehouse.getId(),
                    updateWarehouse.getName(),
                    updateWarehouse.getLocation()
            );
        }

        //Eliminar un almacén
    public void deleteWarehouse (Long id){
        Warehouse warehouse=warehouseRepository.findById(id).orElseThrow(()->new RuntimeException("Warehouse not found"));
        warehouseRepository.delete(warehouse);
    }

}
