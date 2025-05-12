package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.WorkDTO;
import com.example.equipmentmanagement.model.Work;
import org.springframework.stereotype.Component;

@Component
public class WorkMapper {

    //Convertir Work a WorkDTO
    public static WorkDTO toDTO(Work work){
        return new WorkDTO(
                work.getId(),
                work.getName(),
                work.getLocation()
        );
    }

    //Convertir WorkDTo a Work
    public Work toEntity(WorkDTO workDTO) {
        Work work = new Work();
        work.setId(workDTO.getId());
        work.setName(workDTO.getName());
        work.setLocation(workDTO.getLocation());
        return  work;
    }
}
