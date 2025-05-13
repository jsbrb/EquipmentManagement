package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.mapper.WorkMapper;
import com.example.equipmentmanagement.model.Work;
import com.example.equipmentmanagement.repository.WorkRepository;
import com.sun.jdi.PrimitiveValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

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
                work.getLocation()
        );
    }

    //Crear una nueva obra
    public  WorkDTO createdWork(WorkDTO workDTO){
        if(workDTO.getId()!=null){
            throw new RuntimeException("No se puede crear una obra con un ID existente");
        }
        Work work= new Work();
        work.setName(workDTO.getName());
        work.setLocation(workDTO.getLocation());

        Work savedWork= workRepository.save(work);
        return new WorkDTO(
                savedWork.getId(),
                savedWork.getName(),
                savedWork.getLocation()
        );
    }

    //Actualizar una obra
    public WorkDTO updateWork(Long id, WorkDTO workDTO){
        Work work = workRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Obra no encontrada"));

        //Solo actualizamos los valores que vienen en el DTO
        work.setName(workDTO.getName());
        work.setLocation(workDTO.getLocation());

        Work updateWork = workRepository.save(work);
        return new WorkDTO(
                updateWork.getId(),
                updateWork.getName(),
                updateWork.getLocation()
        );
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
