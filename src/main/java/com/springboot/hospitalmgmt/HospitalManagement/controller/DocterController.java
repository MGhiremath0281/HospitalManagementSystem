package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Docter;
import com.springboot.hospitalmgmt.HospitalManagement.service.DocterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/docters")
public class DocterController {

    @Autowired
    private DocterService docterService;

    // Create doctor
    @PostMapping
    public ResponseEntity<Docter> createDocter(@RequestBody Docter docter) {
        Docter savedDocter = docterService.createDocter(docter);
        return ResponseEntity.ok(savedDocter);
    }

    // Get all docters with pagination
    @GetMapping
    public ResponseEntity<Page<Docter>> getAllDocters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Docter> docters = docterService.getAllDocters(page, size);
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
    public ResponseEntity<?> updateDocter(@PathVariable Long id, @RequestBody Docter docter) {
        Docter updatedDocter = docterService.updateDocter(id, docter);

        if (updatedDocter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDocter);
    }

    // Delete docter
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocter(@PathVariable Long id) {
        docterService.deleteDocter(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}
