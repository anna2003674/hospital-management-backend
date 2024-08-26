package com.hospitalproject.service;

import com.hospitalproject.ResourceNotFoundException;
import com.hospitalproject.dto.DoctorDTO;
import com.hospitalproject.dto.PatientDTO;
import com.hospitalproject.model.Doctor;
import com.hospitalproject.model.Patient;
import com.hospitalproject.repository.DoctorRepository;
import com.hospitalproject.repository.PatientRepository;
import com.hospitalproject.repository.PositionRepository;
import org.springframework.stereotype.Service;
import com.hospitalproject.model.Position;
import com.hospitalproject.model.Disease;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PositionRepository positionRepository;
    private final PatientRepository patientRepository;

    public DoctorService(DoctorRepository doctorRepository, PositionRepository positionRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.positionRepository = positionRepository;
        this.patientRepository = patientRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        return convertToDTO(doctor);
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = convertToEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setName(doctorDTO.getName());

        Position position = positionRepository.findByName(doctorDTO.getPosition())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        doctor.setPosition(position);

        doctor.setWorkingHours(doctorDTO.getWorkingHours());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToDTO(updatedDoctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        return new DoctorDTO(doctor.getId(), doctor.getName(), doctor.getPosition().getName(), doctor.getWorkingHours());
    }


    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setWorkingHours(doctorDTO.getWorkingHours());

        Position position = positionRepository.findByName(doctorDTO.getPosition())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        doctor.setPosition(position);

        return doctor;
    }

    public List<Doctor> getDoctorsByIds(List<Long> doctorIds) {
        List<Doctor> doctors = doctorRepository.findAllById(doctorIds);

        if (doctors.size() != doctorIds.size()) {
            throw new ResourceNotFoundException("Some doctors were not found");
        }

        return doctors;
    }

    public void addPatientToDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        doctor.addPatient(patient);
        doctorRepository.save(doctor);
    }

    public void removePatientFromDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        doctor.removePatient(patient);
        doctorRepository.save(doctor);
    }

    public List<PatientDTO> getAllPatientsByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        return doctor.getPatients().stream()
                .map(this::convertToPatientDTO)
                .collect(Collectors.toList());
    }

    private PatientDTO convertToPatientDTO(Patient patient) {
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

}
