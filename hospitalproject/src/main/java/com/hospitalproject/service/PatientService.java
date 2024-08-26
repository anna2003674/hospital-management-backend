package com.hospitalproject.service;

import com.hospitalproject.ResourceNotFoundException;
import com.hospitalproject.dto.DoctorDTO;
import com.hospitalproject.dto.PatientDTO;
import com.hospitalproject.model.Disease;
import com.hospitalproject.model.Doctor;
import com.hospitalproject.model.Patient;
import com.hospitalproject.repository.DiseaseRepository;
import com.hospitalproject.repository.DoctorRepository;
import com.hospitalproject.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;
    private final DoctorRepository doctorRepository;

    public PatientService(PatientRepository patientRepository, DiseaseRepository diseaseRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.diseaseRepository = diseaseRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return convertToDTO(patient);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setAge(patientDTO.getAge());

        List<Disease> diseases = patientDTO.getDiseases().stream()
                .map(diseaseName -> diseaseRepository.findByName(diseaseName)
                        .orElseThrow(() -> new RuntimeException("Disease not found: " + diseaseName)))
                .collect(Collectors.toList());

        patient.setDiseases(diseases);

        Patient savedPatient = patientRepository.save(patient);
        return new PatientDTO(savedPatient.getId(), savedPatient.getName(), savedPatient.getDiseases().stream()
                .map(Disease::getName).collect(Collectors.toList()), savedPatient.getAge());
    }

    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        for (Doctor doctor : patient.getDoctors()) {
            doctor.getPatients().remove(patient);
        }
        patient.getDoctors().clear();

        patientRepository.deleteById(patientId);
    }

    private PatientDTO convertToDTO(Patient patient) {
        List<String> diseaseNames = patient.getDiseases().stream()
                .map(Disease::getName)
                .collect(Collectors.toList());

        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                diseaseNames,
                patient.getAge()
        );
    }

    private Patient convertToEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setAge(patientDTO.getAge());

        List<Disease> diseases = patientDTO.getDiseases().stream()
                .map(diseaseName -> diseaseRepository.findByName(diseaseName)
                        .orElseThrow(() -> new RuntimeException("Disease not found: " + diseaseName)))
                .collect(Collectors.toList());

        patient.setDiseases(diseases);

        return patient;
    }


    public void addDoctorToPatient(Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        patient.addDoctor(doctor);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
    }

    public void removeDoctorFromPatient(Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        patient.removeDoctor(doctor);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
    }

    public List<DoctorDTO> getAllDoctorsByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return patient.getDoctors().stream()
                .map(this::convertToDoctorDTO)
                .collect(Collectors.toList());
    }

    private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getPosition().getName(),
                doctor.getWorkingHours()
        );
    }

}
