package com.example.equipmentmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //Getters and setters

    public void setName(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name){
        this.name=name;
    }

    public String setName(){
        return name;
    }
}
