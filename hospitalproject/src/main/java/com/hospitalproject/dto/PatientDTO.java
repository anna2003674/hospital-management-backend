package com.hospitalproject.dto;

import java.util.List;

public class PatientDTO {
    private Long id;
    private String name;
    private List<String> diseases;
    private int age;

    public PatientDTO(Long id, String name, List<String> diseases, int age) {
        this.id = id;
        this.name = name;
        this.diseases = diseases;
        this.age = age;
    }

    public PatientDTO(String name, List<String> diseases, int age) {
        this.name = name;
        this.diseases = diseases;
        this.age = age;
    }

    public PatientDTO() {
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


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }
}