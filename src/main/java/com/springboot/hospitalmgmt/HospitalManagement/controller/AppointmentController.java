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

import com.springboot.hospitalmgmt.HospitalManagement.dto.AppointmentRequest; // <-- NEW IMPORT for DTO
import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment;
import com.springboot.hospitalmgmt.HospitalManagement.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/private/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    // Create / Update – only doctors and staff
    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF')")
    // 💡 UPDATED: Now accepts AppointmentRequest DTO for robust validation
    public ResponseEntity<Appointment> save(@Valid @RequestBody AppointmentRequest request) {

        // Log the IDs from the DTO instead of trying to access nested objects which
        // might be null
        logger.info("Received request to save appointment for patient ID: {} and doctor ID: {}",
                request.getPatientId(), request.getDoctorId());

        // The service layer now handles mapping the DTO IDs to entities and saving
        Appointment saved = appointmentService.saveAppointment(request);

        logger.info("Appointment saved successfully with ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    // Get All (with pagination) – only doctors and staff
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF')")
    public Page<Appointment> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching paginated list of appointments (page={}, size={})", page, size);
        return appointmentService.getAllAppointments(page, size);
    }

    // Get By ID – only doctor, staff, or the patient who owns it
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF') or @securityService.isAppointmentOwner(authentication, #id)")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        logger.info("Fetching appointment with ID: {}", id);
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // Get By Patient – only the patient themselves or doctor/staff
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF') or principal.id == #patientId")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        logger.info("Fetching appointments for patient ID: {}", patientId);
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    // Get By Doctor – only the doctor themselves or staff
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('STAFF') or principal.id == #doctorId")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        logger.info("Fetching appointments for doctor ID: {}", doctorId);
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    // Delete – only staff
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Request received to delete appointment with ID: {}", id);
        appointmentService.deleteAppointment(id);
        logger.info("Appointment deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}