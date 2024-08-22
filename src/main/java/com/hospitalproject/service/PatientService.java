package com.hospitalproject.service;

import com.hospitalproject.ResourceNotFoundException;
import com.hospitalproject.dto.PatientDTO;
import com.hospitalproject.model.Doctor;
import com.hospitalproject.model.Patient;
import com.hospitalproject.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DoctorService doctorService;

    public PatientService(PatientRepository patientRepository, DoctorService doctorService) {
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
    }

    public PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(patient.getId(), patient.getName(), patient.getDisease(), patient.getAge(), patient.getDoctor().getId());
    }

    public Patient convertToEntity(PatientDTO patientDTO) {
        Doctor doctor = doctorService.convertToEntity(doctorService.getDoctorById(patientDTO.getDoctorId()));
        return new Patient(patientDTO.getId(), patientDTO.getName(), patientDTO.getDisease(), patientDTO.getAge(), doctor);
    }

    public List<PatientDTO> getAllPatientsByDoctorId(Long doctorId) {
        return patientRepository.findByDoctorId(doctorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public PatientDTO savePatient(PatientDTO patientDTO) {
        Patient patient = convertToEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return convertToDTO(savedPatient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }


    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (!existingPatient.isPresent()) {
            throw new ResourceNotFoundException("Patient not found with id " + id);
        }
        Patient patient = existingPatient.get();
        patient.setName(patientDTO.getName());
        patient.setDisease(patientDTO.getDisease());
        patient.setAge(patientDTO.getAge());

        Doctor doctor = doctorService.convertToEntity(doctorService.getDoctorById(patientDTO.getDoctorId()));
        patient.setDoctor(doctor);

        Patient updatedPatient = patientRepository.save(patient);
        return convertToDTO(updatedPatient);
    }

}
