package com.example.equipmentmanagement.dto;

public class WorkDTO {

    private Long id;
    private String name;
    private String location;

    //Constructor vacío
    public WorkDTO(){}

    //Constructor
    public WorkDTO(Long id, String name, String location){
        this.id=id;
        this.name=name;
        this.location=location;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
