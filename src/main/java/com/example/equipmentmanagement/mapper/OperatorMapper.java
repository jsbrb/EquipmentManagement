package com.example.equipmentmanagement.mapper;

import com.example.equipmentmanagement.dto.OperatorDTO;
import com.example.equipmentmanagement.model.Operator;
import org.springframework.stereotype.Component;

@Component
public class OperatorMapper {

    //Convertir Operator a Operator DTO
    public static OperatorDTO toDTO(Operator operator){
        return new OperatorDTO(
                operator.getId(),
                operator.getName()
        );
    }

    //Convertir OperatorDTO a Operator
    public Operator toEntity(OperatorDTO operatorDTO){
        Operator operator= new Operator();
        operator.setId(operatorDTO.getId());
        operator.setName(operatorDTO.getName());

        return operator;
    }


}
