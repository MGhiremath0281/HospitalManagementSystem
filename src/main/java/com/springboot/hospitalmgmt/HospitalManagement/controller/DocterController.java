package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.service.DocterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctor/api")
public class DocterController {

    @Autowired
    private DocterService docterService;

    // Only ADMIN can create doctor
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Doctor> createDocter(@Valid @RequestBody Doctor doctor) {
        Doctor savedDoctor = docterService.createDocter(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    // ADMIN and DOCTOR can view all doctors
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public ResponseEntity<Page<Doctor>> getAllDocters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Doctor> docters = docterService.getAllDocters(page, size);
        return ResponseEntity.ok(docters);
    }

    // ADMIN and DOCTOR can view doctor by ID
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocterById(@PathVariable Long id) {
        return docterService.getDocterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Only ADMIN can update doctor
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocter(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        Doctor updatedDoctor = docterService.updateDocter(id, doctor);

        if (updatedDoctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDoctor);
    }

    // Only ADMIN can delete doctor
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocter(@PathVariable Long id) {
        docterService.deleteDocter(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}
