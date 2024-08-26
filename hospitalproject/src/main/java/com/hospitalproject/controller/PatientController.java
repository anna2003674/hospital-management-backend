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
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/patients/{patientId}/doctors/{doctorId}")
    public ResponseEntity<Void> addDoctorToPatient(@PathVariable Long patientId, @PathVariable Long doctorId) {
        patientService.addDoctorToPatient(patientId, doctorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/patients/{patientId}/doctors/{doctorId}")
    public ResponseEntity<Void> removeDoctorFromPatient(@PathVariable Long patientId, @PathVariable Long doctorId) {
        patientService.removeDoctorFromPatient(patientId, doctorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients/{patientId}/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctorsByPatientId(@PathVariable Long patientId) {
        List<DoctorDTO> doctors = patientService.getAllDoctorsByPatientId(patientId);
        return ResponseEntity.ok(doctors);
    }

}
