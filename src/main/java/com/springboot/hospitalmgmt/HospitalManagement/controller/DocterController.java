package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.service.DocterService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/doctors")
public class DocterController {

    @Autowired
    private DocterService docterService;

    // Create doctor
    @PostMapping
    public ResponseEntity<Doctor> createDocter(@Valid @RequestBody Doctor doctor) {
        Doctor savedDoctor = docterService.createDocter(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    // Get all docters with pagination
    @GetMapping
    public ResponseEntity<Page<Doctor>> getAllDocters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Doctor> docters = docterService.getAllDocters(page, size);
        return ResponseEntity.ok(docters);
    }

    // Get docter by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocterById(@PathVariable Long id) {
        return docterService.getDocterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update docter
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocter(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        Doctor updatedDoctor = docterService.updateDocter(id, doctor);

        if (updatedDoctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDoctor);
    }

    // Delete docter
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocter(@PathVariable Long id) {
        docterService.deleteDocter(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}
