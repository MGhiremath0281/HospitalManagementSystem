package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Insurance;
import com.springboot.hospitalmgmt.HospitalManagement.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
