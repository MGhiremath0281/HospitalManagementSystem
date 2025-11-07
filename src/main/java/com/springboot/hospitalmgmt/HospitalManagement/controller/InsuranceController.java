package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.models.Insurance;
import com.springboot.hospitalmgmt.HospitalManagement.service.InsuranceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    // Only ADMIN or DOCTOR can assign insurance to a patient
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PostMapping("/assign/{patientId}")
    public ResponseEntity<String> assignInsuranceToPatient(
            @PathVariable("patientId") Long patientId,
            @RequestBody Insurance insurance) {
        try {
            insuranceService.assignInsuranceToPatient(insurance, patientId);
            return ResponseEntity.ok("Insurance assigned successfully to patient with ID: " + patientId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
