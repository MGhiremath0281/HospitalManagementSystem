package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import com.springboot.hospitalmgmt.HospitalManagement.service.ApointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/v1/apointments")
public class ApointmentController {

    @Autowired
    private ApointmentService apointmentService;

    @PostMapping
    public ResponseEntity<Apointment> createApointment(@RequestBody Apointment apointment) {
        Apointment savedApointment = apointmentService.saveApointment(apointment);
        return ResponseEntity.ok(savedApointment);
    }

    @GetMapping
    public ResponseEntity<Page<Apointment>> getAllApointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        return ResponseEntity.ok(apointmentService.getAllApointments(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apointment> getApointmentById(@PathVariable Long id) {
        return apointmentService.getApointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Apointment>> getApointmentsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(apointmentService.getApointmentsByPatientId(patientId));
    }

    @GetMapping("/docter/{docterId}")
    public ResponseEntity<List<Apointment>> getApointmentsByDocterId(@PathVariable Long docterId) {
        return ResponseEntity.ok(apointmentService.getApointmentsByDocterId(docterId));
    }

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
