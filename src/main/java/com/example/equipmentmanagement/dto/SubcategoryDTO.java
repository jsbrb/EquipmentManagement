package com.example.equipmentmanagement.dto;

public class SubcategoryDTO {
    private Long id;
    private String name;

    //Constructor vacío
    public SubcategoryDTO (){};

    //Constructor
    public SubcategoryDTO (Long id, String name){
        this.id=id;
        this.name=name;
    }

    public SubcategoryDTO(String name) {
        this.name = name;
    }

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
}
