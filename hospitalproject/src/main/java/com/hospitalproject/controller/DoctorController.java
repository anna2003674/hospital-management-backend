package com.hospitalproject.controller;

import com.hospitalproject.dto.DoctorDTO;
import com.hospitalproject.dto.PatientDTO;
import com.hospitalproject.service.DoctorService;
import com.hospitalproject.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;

    public DoctorController(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("/doctors")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/doctors")
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO newDoctor = doctorService.createDoctor(doctorDTO);
        return ResponseEntity.status(201).body(newDoctor);
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(id, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }


    @PostMapping("/doctors/{doctorId}/patients/{patientId}")
    public ResponseEntity<Void> addPatientToDoctor(@PathVariable Long doctorId, @PathVariable Long patientId) {
        doctorService.addPatientToDoctor(doctorId, patientId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/doctors/{doctorId}/patients/{patientId}")
    public ResponseEntity<Void> removePatientFromDoctor(@PathVariable Long doctorId, @PathVariable Long patientId) {
        doctorService.removePatientFromDoctor(doctorId, patientId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/doctors/{doctorId}/patients")
    public ResponseEntity<List<PatientDTO>> getPatientsByDoctorId(@PathVariable Long doctorId) {
        List<PatientDTO> patients = doctorService.getAllPatientsByDoctorId(doctorId);
        return ResponseEntity.ok(patients);
    }

    @PostMapping("/doctors/{doctorId}/patients")
    public ResponseEntity<PatientDTO> createPatientForDoctor(@PathVariable Long doctorId, @RequestBody PatientDTO patientDTO) {
        try {
            PatientDTO newPatient = patientService.createPatient(patientDTO);
            doctorService.addPatientToDoctor(doctorId, newPatient.getId());
            return ResponseEntity.status(201).body(newPatient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

}
