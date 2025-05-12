package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.WarehouseDTO;
import com.example.equipmentmanagement.model.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {
    //Convertir Warehouse a WarehouseDTO
    public static WarehouseDTO toDTO(Warehouse warehouse){
        return new WarehouseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation()
        );
    }

    //Convetir WarehouseDTO a Warehouse
    public Warehouse toEntity(WarehouseDTO warehouseDTO){
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDTO.getId());
        warehouse.setName(warehouseDTO.getName());
        warehouse.setLocation(warehouseDTO.getLocation());

        return warehouse;
    }

}
