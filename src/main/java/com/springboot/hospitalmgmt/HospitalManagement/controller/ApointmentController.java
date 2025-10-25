package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import com.springboot.hospitalmgmt.HospitalManagement.service.ApointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/apointments")
public class ApointmentController {

    @Autowired
    private ApointmentService apointmentService;

    // Create new appointment
    @PostMapping
    public ResponseEntity<Apointment> createApointment(@RequestBody Apointment apointment) {
        Apointment savedApointment = apointmentService.saveApointment(apointment);
        return ResponseEntity.ok(savedApointment);
    }

    // Get all appointments
    @GetMapping
    public ResponseEntity<List<Apointment>> getAllApointments() {
        return ResponseEntity.ok(apointmentService.getAllApointments());
    }

    // Get appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Apointment> getApointmentById(@PathVariable Long id) {
        return apointmentService.getApointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get appointments by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Apointment>> getApointmentsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(apointmentService.getApointmentsByPatientId(patientId));
    }

    // Get appointments by doctor ID
    @GetMapping("/docter/{docterId}")
    public ResponseEntity<List<Apointment>> getApointmentsByDocterId(@PathVariable Long docterId) {
        return ResponseEntity.ok(apointmentService.getApointmentsByDocterId(docterId));
    }

    // Update appointment
    @PutMapping("/{id}")
    public ResponseEntity<Apointment> updateApointment(@PathVariable Long id, @RequestBody Apointment apointment) {
        return apointmentService.getApointmentById(id)
                .map(existingApointment -> {
                    existingApointment.setPatientId(apointment.getPatientId());
                    existingApointment.setDocterId(apointment.getDocterId());
                    existingApointment.setDate(apointment.getDate());
                    Apointment updatedApointment = apointmentService.saveApointment(existingApointment);
                    return ResponseEntity.ok(updatedApointment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApointment(@PathVariable Long id) {
        if (apointmentService.getApointmentById(id).isPresent()) {
            apointmentService.deleteApointment(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
