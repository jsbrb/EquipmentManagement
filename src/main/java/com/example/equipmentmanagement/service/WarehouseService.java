package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.EquipmentDTO;
import com.example.equipmentmanagement.dto.WarehouseDTO;
import com.example.equipmentmanagement.mapper.WarehouseMapper;
import com.example.equipmentmanagement.mapper.WorkMapper;
import com.example.equipmentmanagement.model.EquipmentStatus;
import com.example.equipmentmanagement.model.Warehouse;
import com.example.equipmentmanagement.model.Work;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import com.example.equipmentmanagement.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private EquipmentRepository equipmentRepository;

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
                warehouse.getLocation(),
                warehouse.getLatitude(),
                warehouse.getLongitude()
        );
    }

    //Crear un nuevo almacén
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        if (warehouseDTO.getId() != null) {
            throw new RuntimeException("No se puede crear un almacén con un ID exitente");
        }

        Warehouse warehouse = WarehouseMapper.toEntity(warehouseDTO);

        // Aquí añadimos geocodificación para obtener latitud y longitud
        try {
            Optional<double[]> coords = geocodingService.getLatLonFromAddress(warehouseDTO.getLocation());
            if (coords.isPresent()) {
                double[] latlon = coords.get();
                warehouse.setLatitude(latlon[0]);
                warehouse.setLongitude(latlon[1]);
            }
        } catch (Exception e) {
            System.err.println("No se pudo geocodificar la dirección: " + e.getMessage());
        }

        /*Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseDTO.getName());
        warehouse.setLocation(warehouseDTO.getLocation());

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        System.out.println("Almacén guardado con ID: " + savedWarehouse.getId());
        return new WarehouseDTO(
                savedWarehouse.getId(),
                savedWarehouse.getName(),
                savedWarehouse.getLocation(),
                savedWarehouse.getLatitude(),
                savedWarehouse.getLongitude()*/

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return WarehouseMapper.toDTO(savedWarehouse);
    }

        //Actualizar un almacén
        public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO){
            Warehouse warehouse= warehouseRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Almacén no encontrado"));

            //Solo actualizamos los valores que vienen en el DTO
            warehouse.setName(warehouseDTO.getName());
            warehouse.setLocation(warehouseDTO.getLocation());

            /*
            Warehouse updateWarehouse = warehouseRepository.save(warehouse);
            return new WarehouseDTO(
                    updateWarehouse.getId(),
                    updateWarehouse.getName(),
                    updateWarehouse.getLocation(),
                    updateWarehouse.getLatitude(),
                    updateWarehouse.getLongitude()
            );
            */

            // Actualizar coordenadas si cambia la localización
            try {
                Optional<double[]> coords = geocodingService.getLatLonFromAddress(warehouseDTO.getLocation());
                if (coords.isPresent()) {
                    double[] latlon = coords.get();
                    warehouse.setLatitude(latlon[0]);
                    warehouse.setLongitude(latlon[1]);
                }
            } catch (Exception e) {
                System.err.println("No se pudo geocodificar la dirección: " + e.getMessage());
            }

            Warehouse updateWarehouse = warehouseRepository.save(warehouse);
            return WarehouseMapper.toDTO(updateWarehouse);
        }

        //Eliminar un almacén
    public void deleteWarehouse (Long id){
        Warehouse warehouse=warehouseRepository.findById(id).orElseThrow(()->new RuntimeException("Warehouse not found"));
        warehouseRepository.delete(warehouse);
    }

    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con id: " + id));
    }

    //Método para obtener los equipos de cada almacén
    public List<EquipmentDTO> getAvailableEquipmentByWarehouseId(Long warehouseId) {
        return equipmentRepository.findByWarehouseId(warehouseId).stream()
                .filter(e -> EquipmentStatus.DISPONIBLE.equals(e.getCurrentStatus()))
                .map(e -> new EquipmentDTO(e.getId(), e.getName()))
                .toList();
    }

}
