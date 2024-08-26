package com.hospitalproject.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Column(name = "working_hours")
    private String workingHours;

    @ManyToMany
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(Long id, String name, Position position, String workingHours, List<Patient> patients) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.workingHours = workingHours;
        this.patients = patients;
    }

    public Doctor(String name, Position position, String workingHours, List<Patient> patients) {
        this.name = name;
        this.position = position;
        this.workingHours = workingHours;
        this.patients = patients;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        if (!patients.contains(patient)) {
            patients.add(patient);
            patient.getDoctors().add(this);
        }
    }

    public void removePatient(Patient patient) {
        if (patients.contains(patient)) {
            patients.remove(patient);
            patient.getDoctors().remove(this);
        }
    }

}
