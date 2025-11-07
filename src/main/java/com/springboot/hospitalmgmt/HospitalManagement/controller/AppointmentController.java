package com.springboot.hospitalmgmt.HospitalManagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment;
import com.springboot.hospitalmgmt.HospitalManagement.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/api")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    // Create / Update appointment
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @PostMapping
    public ResponseEntity<Appointment> save(@Valid @RequestBody Appointment appointment) {
        Long patientId = (appointment.getPatient() != null) ? appointment.getPatient().getId() : null;
        logger.info("Received request to save appointment for patient ID: {}", patientId);

        Appointment saved = appointmentService.saveAppointment(appointment);
        logger.info("Appointment saved successfully with ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    // Get All appointments (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<Appointment> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching paginated list of appointments (page={}, size={})", page, size);
        return appointmentService.getAllAppointments(page, size);
    }

    // Get appointment by ID (ADMIN & DOCTOR can view any)
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        logger.info("Fetching appointment with ID: {}", id);
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // Get appointments by patient (ADMIN, DOCTOR, and PATIENT can view)
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR') or (hasRole('PATIENT') and #patientId == authentication.principal.id)")
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        logger.info("Fetching appointments for patient ID: {}", patientId);
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    // Get appointments by doctor (ADMIN and DOCTOR only)
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        logger.info("Fetching appointments for doctor ID: {}", doctorId);
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    // Delete appointment (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Request received to delete appointment with ID: {}", id);
        appointmentService.deleteAppointment(id);
        logger.info("Appointment deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
