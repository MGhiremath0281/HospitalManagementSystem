package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.AppointmentRequest;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.AppointmentNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.ResourceNotFoundException; // <-- NOW REQUIRED
import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment;
import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.AppointmentRepository;
import com.springboot.hospitalmgmt.HospitalManagement.repository.DocterRepository; // <-- CORRECTED IMPORT (Based on your project structure)
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 💡 CRITICAL FIX: Injected DocterRepository
    @Autowired
    private DocterRepository docterRepository;

    @Autowired
    private PatientRepository patientRepository; // Assuming this exists

    @Override
    public Appointment saveAppointment(AppointmentRequest request) {
        logger.info("Processing appointment creation for Doctor ID: {} and Patient ID: {}",
                request.getDoctorId(), request.getPatientId());

        // 1. Fetch Doctor Entity
        Doctor doctor = docterRepository.findById(request.getDoctorId()) // <-- USING docterRepository
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", request.getDoctorId()));

        // 2. Fetch Patient Entity
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", request.getPatientId()));

        // 3. Build the Appointment Entity (Doctor is now guaranteed non-null)
        Appointment appointment = Appointment.builder()
                .date(request.getDate())
                .doctor(doctor)
                .patient(patient)
                .build();

        // 4. Save the entity
        Appointment saved = appointmentRepository.save(appointment);
        logger.info("New appointment created successfully with ID: {}", saved.getId());
        return saved;
    }

    // --- EXISTING METHODS ---
    @Override
    public Page<Appointment> getAllAppointments(int page, int size) {
        logger.debug("Fetching all appointments (page={}, size={})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return appointmentRepository.findAll(pageable);
    }

    @Override
    public Appointment getAppointmentById(Long id) throws AppointmentNotFoundException {
        logger.info("Fetching appointment by ID: {}", id);
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        logger.info("Fetching all appointments for patient ID: {}", patientId);
        return appointmentRepository.findByPatient_Id(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        logger.info("Fetching all appointments for doctor ID: {}", doctorId);
        return appointmentRepository.findByDoctor_Id(doctorId);
    }

    @Override
    public void deleteAppointment(Long id) {
        logger.warn("Deleting appointment with ID: {}", id);
        appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Can't delete - Appointment with ID: {} not found", id);
                    return new AppointmentNotFoundException(id);
                });
        appointmentRepository.deleteById(id);
        logger.info("Successfully deleted the appointment with ID: {}", id);
    }
}