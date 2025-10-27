package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import com.springboot.hospitalmgmt.HospitalManagement.service.ApointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class ApointmentController {

    private static final Logger logger = LoggerFactory.getLogger(ApointmentController.class);

    @Autowired
    private ApointmentService apointmentService;

    // Create / Update
    @PostMapping
    public ResponseEntity<Apointment> save(@RequestBody Apointment apointment) {
        logger.info("Received request to save appointment for patient ID: {}", apointment.getPatientId());
        Apointment saved = apointmentService.saveApointment(apointment);
        logger.info("Appointment saved successfully with ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    // Get All (with pagination)
    @GetMapping
    public Page<Apointment> getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching paginated list of appointments (page={}, size={})", page, size);
        return apointmentService.getAllApointments(page, size);
    }

    // Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<Apointment> getById(@PathVariable Long id) {
        logger.info("Fetching appointment with ID: {}", id);
        Optional<Apointment> apointment = apointmentService.getApointmentById(id);
        return apointment.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Appointment not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Get By Patient
    @GetMapping("/patient/{patientId}")
    public List<Apointment> getByPatient(@PathVariable Long patientId) {
        logger.info("Fetching appointments for patient ID: {}", patientId);
        return apointmentService.getApointmentsByPatientId(patientId);
    }

    // Get By Doctor
    @GetMapping("/doctor/{doctorId}")
    public List<Apointment> getByDoctor(@PathVariable Long doctorId) {
        logger.info("Fetching appointments for doctor ID: {}", doctorId);
        return apointmentService.getApointmentsByDocterId(doctorId);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Request received to delete appointment with ID: {}", id);
        apointmentService.deleteApointment(id);
        logger.info("Appointment deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
