package com.hospitalproject.service;

import com.hospitalproject.ResourceNotFoundException;
import com.hospitalproject.dto.DoctorDTO;
import com.hospitalproject.model.Doctor;
import com.hospitalproject.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorDTO convertToDTO(Doctor doctor) {
        return new DoctorDTO(doctor.getId(), doctor.getName(), doctor.getPosition(), doctor.getWorkingHours());
    }

    public Doctor convertToEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setPosition(doctorDTO.getPosition());
        doctor.setWorkingHours(doctorDTO.getWorkingHours());
        return doctor;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = convertToEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(id);
        if (!existingDoctor.isPresent()) {
            throw new ResourceNotFoundException("Doctor not found with id " + id);
        }

        Doctor doctor = existingDoctor.get();
        doctor.setName(doctorDTO.getName());
        doctor.setPosition(doctorDTO.getPosition());
        doctor.setWorkingHours(doctorDTO.getWorkingHours());
        doctorRepository.save(doctor);
        return convertToDTO(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
