package com.hospitalproject.dto;

public class DoctorDTO {
    private Long id;
    private String name;
    private String position;
    private String workingHours;

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String name, String position, String workingHours) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.workingHours = workingHours;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
