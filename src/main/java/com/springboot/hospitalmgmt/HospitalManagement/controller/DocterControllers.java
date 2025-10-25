package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Docter;
import com.springboot.hospitalmgmt.HospitalManagement.service.DocterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docters")
public class DocterControllers {

    @Autowired
    private DocterService docterService;

    // Create new docter
    @PostMapping
    public ResponseEntity<Docter> createDocter(@RequestBody Docter docter) {
        Docter savedDocter = docterService.saveDocter(docter);
        return ResponseEntity.ok(savedDocter);
    }

    // Get all docters
    @GetMapping
    public ResponseEntity<List<Docter>> getAllDocters() {
        return ResponseEntity.ok(docterService.getAllDocters());
    }

    // Get docter by ID
    @GetMapping("/{id}")
    public ResponseEntity<Docter> getDocterById(@PathVariable Long id) {
        return docterService.getDocterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update docter
    @PutMapping("/{id}")
    public ResponseEntity<Docter> updateDocter(@PathVariable Long id, @RequestBody Docter docter) {
        return docterService.getDocterById(id)
                .map(existingDocter -> {
                    existingDocter.setName(docter.getName());
                    existingDocter.setSpacility(docter.getSpacility());
                    Docter updatedDocter = docterService.saveDocter(existingDocter);
                    return ResponseEntity.ok(updatedDocter);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete docter
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocter(@PathVariable Long id) {
        if (docterService.getDocterById(id).isPresent()) {
            docterService.deleteDocter(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
