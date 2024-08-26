package com.hospitalproject.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @ManyToMany
    @JoinTable(
            name = "patient_disease",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private List<Disease> diseases = new ArrayList<>();

    @ManyToMany(mappedBy = "patients", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Doctor> doctors = new ArrayList<>();

    public Patient() {
    }

    public Patient(String name, int age, List<Disease> diseases, List<Doctor> doctors) {
        this.name = name;
        this.age = age;
        this.diseases = diseases;
        this.doctors = doctors;
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

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }


    public void addDisease(Disease disease) {
        if (!diseases.contains(disease)) {
            diseases.add(disease);
            disease.getPatients().add(this);
        }
    }

    public void removeDisease(Disease disease) {
        if (diseases.contains(disease)) {
            diseases.remove(disease);
            disease.getPatients().remove(this);
        }
    }

    public void addDoctor(Doctor doctor) {
        if (!doctors.contains(doctor)) {
            doctors.add(doctor);
            doctor.getPatients().add(this);
        }
    }

    public void removeDoctor(Doctor doctor) {
        if (doctors.contains(doctor)) {
            doctors.remove(doctor);
            doctor.getPatients().remove(this);
        }
    }


}


