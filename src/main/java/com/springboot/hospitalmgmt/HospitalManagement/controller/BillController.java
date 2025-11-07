package com.springboot.hospitalmgmt.HospitalManagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.service.BillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    // Only ADMIN can create a bill
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BillResponseDTO> createBill(@Valid @RequestBody BillRequestDTO billRequestDTO) {
        logger.info("Creating new bill for patient ID: {}", billRequestDTO.getPatientId());
        BillResponseDTO responseDTO = billService.createBill(billRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // ADMIN and DOCTOR can view all bills
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public ResponseEntity<Page<BillResponseDTO>> getAllBills(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        logger.info("Fetching all bills");
        Page<BillResponseDTO> bills = billService.getAllBills(pageable);
        return ResponseEntity.ok(bills);
    }

    // ADMIN and DOCTOR can view bill by ID
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        logger.info("Fetching bill with ID: {}", id);
        Bill bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    // ADMIN and DOCTOR can view bills by patient ID
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bill>> getBillsByPatientId(@PathVariable Long patientId) {
        logger.info("Fetching bills for patient ID: {}", patientId);
        List<Bill> bills = billService.getBillsByPatientId(patientId);
        return ResponseEntity.ok(bills);
    }

    // Only ADMIN can update bill
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BillResponseDTO> updateBill(
            @PathVariable Long id,
            @Valid @RequestBody BillRequestDTO billRequestDTO) {

        logger.info("Updating bill with ID: {}", id);
        BillResponseDTO updatedBillDTO = billService.updateBill(id, billRequestDTO);
        return ResponseEntity.ok(updatedBillDTO);
    }

    // Only ADMIN can delete bill
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        logger.info("Deleting bill with ID: {}", id);
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}
