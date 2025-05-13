package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.dto.OperatorDTO;
import com.example.equipmentmanagement.mapper.OperatorMapper;
import com.example.equipmentmanagement.model.Operator;
import com.example.equipmentmanagement.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    //Obtener todos los operarios
    public List<OperatorDTO> getAllOperators(){
        List<Operator> operators= operatorRepository.findAll();
        System.out.println("Operadores cargados: " + operators);
        return operators.stream().map(OperatorMapper::toDTO).collect(Collectors.toList());
    }

    //Obtener un usuario por ID
    public OperatorDTO getOperatorById(Long id){
        Operator operator = operatorRepository.findById(id).orElseThrow(()->new RuntimeException("Operator not found"));
        return  new OperatorDTO(
                operator.getId(),
                operator.getName()
        );
    }

    //Registrar un nuevo operario
    public OperatorDTO createOperator(OperatorDTO operatorDTO){
        if(operatorDTO.getId() != null){
            throw  new RuntimeException("No se puede registrar un operario con un ID existente.");
        }

        Operator operator =  new Operator();
        operator.setName(operatorDTO.getName());

        Operator savedOperator = operatorRepository.save(operator);
        return  new OperatorDTO(
                savedOperator.getId(),
                savedOperator.getName()
        );
    }

    //Actualizar un operario
    public OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO){
        Operator operator=operatorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Operario no encontrado"));

        //Solo actualizamos el valor que viene al DTO
        operator.setName(operatorDTO.getName());

        Operator updateOperator=operatorRepository.save(operator);
        return new OperatorDTO(
                updateOperator.getId(),
                updateOperator.getName()
        );
    }

    //Eliminar un operario
    public void deleteOperator(Long id){
        Operator operator=operatorRepository.findById(id).orElseThrow(()-> new RuntimeException("Operario not found"));
        operatorRepository.delete(operator);
    }

    public Operator findById(Long id) {
        return operatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operario no encontrado con id: " + id));
    }

}
