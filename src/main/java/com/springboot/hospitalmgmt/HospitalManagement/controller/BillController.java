package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.service.BillService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Bill> createBill(@Valid @RequestBody Bill bill) {
        logger.info("Creating new bill for patient ID: {}", bill.getPatientId());
        Bill savedBill = billService.createBill(bill);
        // MISTAKE CORRECTED: Use HttpStatus.CREATED (201) instead of ResponseEntity.ok() (200)
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    // ✅ Get all bills
    @GetMapping
    public ResponseEntity<Page<Bill>> getAllBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size // Common default size is 20
    ) {
        logger.info("Fetching all bills with page={} and size={}", page, size);
        Page<Bill> bills = billService.getAllBills(page, size);
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
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @Valid @RequestBody Bill bill) {
        logger.info("Updating bill with ID: {}", id);
        Bill updatedBill = billService.updateBill(id, bill);
        return ResponseEntity.ok(updatedBill);
    }

    // ✅ Delete bill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        logger.info("Deleting bill with ID: {}", id);
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}