package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.service.BillService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus; // Import HttpStatus for 201 response
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    // ✅ Create new bill
    @PostMapping
    public ResponseEntity<BillResponseDTO> createBill(@Valid @RequestBody BillRequestDTO billRequestDTO) {
        logger.info("Creating new bill for patient ID: {}", billRequestDTO.getPatientId());

        BillResponseDTO responseDTO = billService.createBill(billRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    // ✅ Get all bills
    @GetMapping
    public ResponseEntity<Page<BillResponseDTO>> getAllBills(
            @PageableDefault(size = 10,page = 0)Pageable pageable
            ) {
        logger.info("Fetching all bills");
        Page<BillResponseDTO> bills = billService.getAllBills(pageable);
        return ResponseEntity.ok(bills);
    }

    // ✅ Get bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        logger.info("Fetching bill with ID: {}", id);
        Bill bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    // ✅ Get bills by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bill>> getBillsByPatientId(@PathVariable Long patientId) {
        logger.info("Fetching bills for patient ID: {}", patientId);
        List<Bill> bills = billService.getBillsByPatientId(patientId);
        return ResponseEntity.ok(bills);
    }

    // ✅ Update existing bill
    // Controller Layer
    @PutMapping("/{id}")
    public ResponseEntity<BillResponseDTO> updateBill(
            // Changed return type to DTO
            @PathVariable Long id,
            @Valid @RequestBody BillRequestDTO billRequestDTO) {

        logger.info("Updating bill with ID: {}", id);

        // Corrected variable type to match the service return type
        BillResponseDTO updatedBillDTO = billService.updateBill(id, billRequestDTO);

        // Return the DTO in the response body with HTTP 200 OK
        return ResponseEntity.ok(updatedBillDTO);
    }

    // ✅ Delete bill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        logger.info("Deleting bill with ID: {}", id);
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}