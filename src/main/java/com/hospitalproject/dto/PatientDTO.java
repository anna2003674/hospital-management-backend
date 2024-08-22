package com.hospitalproject.dto;

public class PatientDTO {
    private Long id;
    private String name;
    private String disease;
    private int age;
    private Long doctorId;

    public PatientDTO() {
    }

    public PatientDTO(Long id, String name, String disease, int age, Long doctorId) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.age = age;
        this.doctorId = doctorId;
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

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }


}