package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.mapper.WorkMapper;
import com.example.equipmentmanagement.model.Work;
import com.example.equipmentmanagement.repository.WorkRepository;
import com.sun.jdi.PrimitiveValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private GeocodingService geocodingService;

    //Obtener todas las obras
    public List<WorkDTO> getAllWorks(){
        List<Work> works = workRepository.findAll();
        return works.stream().map(WorkMapper::toDTO).collect(Collectors.toList());
    }

    //Obtener una obra por ID
    public WorkDTO getWorkById(Long id){
        Work work = workRepository.findById(id).orElseThrow(()-> new RuntimeException("Work not found"));
        return new WorkDTO(
                work.getId(),
                work.getName(),
                work.getLocation(),
                work.getLatitude(),
                work.getLongitude()
        );
    }

    //Crear una nueva obra
    public  WorkDTO createdWork(WorkDTO workDTO){
        if(workDTO.getId()!=null){
            throw new RuntimeException("No se puede crear una obra con un ID existente");
        }
        //Work work= new Work();
        //work.setName(workDTO.getName());
        //work.setLocation(workDTO.getLocation());
        Work work = WorkMapper.toEntity(workDTO);

        // Aquí añadimos geocodificación para obtener latitud y longitud
        try {
            Optional<double[]> coords = geocodingService.getLatLonFromAddress(workDTO.getLocation());
            if (coords.isPresent()) {
                double[] latlon = coords.get();
                work.setLatitude(latlon[0]);
                work.setLongitude(latlon[1]);
            }
        } catch (Exception e) {
            System.err.println("No se pudo geocodificar la dirección: " + e.getMessage());
        }


        Work savedWork= workRepository.save(work);
        return WorkMapper.toDTO(savedWork);
        /*return new WorkDTO(
                savedWork.getId(),
                savedWork.getName(),
                savedWork.getLocation(),
                savedWork.getLatitude(),
                savedWork.getLongitude()
        );*/
    }

    //Actualizar una obra
    public WorkDTO updateWork(Long id, WorkDTO workDTO){
        Work work = workRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Obra no encontrada"));

        //Solo actualizamos los valores que vienen en el DTO
        work.setName(workDTO.getName());
        work.setLocation(workDTO.getLocation());

        // Actualizar coordenadas si cambia la localización
        try {
            Optional<double[]> coords = geocodingService.getLatLonFromAddress(workDTO.getLocation());
            if (coords.isPresent()) {
                double[] latlon = coords.get();
                work.setLatitude(latlon[0]);
                work.setLongitude(latlon[1]);
            }
        } catch (Exception e) {
            System.err.println("No se pudo geocodificar la dirección: " + e.getMessage());
        }

        Work updateWork = workRepository.save(work);
        return WorkMapper.toDTO(updateWork);
        /*return new WorkDTO(
                updateWork.getId(),
                updateWork.getName(),
                updateWork.getLocation()
        );*/
    }

    //Eliminar una obra
    public void deleteWork(Long id){
        Work work = workRepository.findById(id).orElseThrow(()->new RuntimeException("Work not found"));
        workRepository.delete(work);
    }

    public Work findById(Long id) {
        return workRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajo no encontrado con id: " + id));
    }

}
