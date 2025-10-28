package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.service.implementation.BillServiceimpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
public class BillControllers {

    @Autowired
    private BillServiceimpl billService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@Valid  @RequestBody Bill bill) {
        Bill savedBill = billService.saveBill(bill);
        return ResponseEntity.ok(savedBill);
    }

    @GetMapping
    public ResponseEntity<Page<Bill>> getAllBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        return ResponseEntity.ok(billService.getAllBills(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billService.getBillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bill>> getBillsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(billService.getBillsByPatientId(patientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id,@Valid  @RequestBody Bill bill) {
        return billService.getBillById(id)
                .map(existingBill -> {
                    existingBill.setPatientId(bill.getPatientId());
                    existingBill.setAmount(bill.getAmount());
                    existingBill.setStatus(bill.getStatus());
                    Bill updatedBill = billService.saveBill(existingBill);
                    return ResponseEntity.ok(updatedBill);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        if (billService.getBillById(id).isPresent()) {
            billService.deleteBill(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
