package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.models.Insurance;
import com.springboot.hospitalmgmt.HospitalManagement.service.InsuranceService;

@RestController
@RequestMapping("/api/insurance")

public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    // Assign an insurance to a patient by patient ID
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
