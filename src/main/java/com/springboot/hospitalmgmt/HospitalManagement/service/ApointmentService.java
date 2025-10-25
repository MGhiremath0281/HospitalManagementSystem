package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import com.springboot.hospitalmgmt.HospitalManagement.repository.ApointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ApointmentService {

    @Autowired
    private ApointmentRepository apointmentRepository;

    // Create or update appointment
    public Apointment saveApointment(Apointment apointment) {
        return apointmentRepository.save(apointment);
    }

    // Get all appointments
    public Page<Apointment> getAllApointments(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return apointmentRepository.findAll(pageable);
    }

    // Get appointment by ID
    public Optional<Apointment> getApointmentById(Long id) {
        return apointmentRepository.findById(id);
    }

    // Get all appointments by patient ID
    public List<Apointment> getApointmentsByPatientId(Long patientId) {
        return apointmentRepository.findByPatientId(patientId);
    }

    // Get all appointments by doctor ID
    public List<Apointment> getApointmentsByDocterId(Long docterId) {
        return apointmentRepository.findByDocterId(docterId);
    }

    // Delete appointment by ID
    public void deleteApointment(Long id) {
        apointmentRepository.deleteById(id);
    }
}
