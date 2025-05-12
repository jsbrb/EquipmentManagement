package com.example.equipmentmanagement.dto;

public class OperatorDTO {
    private Long id;
    private String name;

    //Constructor vacío
    public OperatorDTO(){}

    public OperatorDTO(Long id, String name) {
        this.id=id;
        this.name=name;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){ return name;}

    public void setName (String name){this.name=name;}
}
