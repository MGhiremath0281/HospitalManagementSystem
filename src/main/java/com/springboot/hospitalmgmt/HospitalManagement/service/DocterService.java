package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Docter;
import com.springboot.hospitalmgmt.HospitalManagement.repository.DocterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;      
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Service
public class DocterService {

    @Autowired
    private DocterRepository docterRepository;

    // Create or update docter
    public Docter saveDocter(Docter docter) {
        return docterRepository.save(docter);
    }

    // Get all docters with pagination
    public Page<Docter> getAllDocters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); 
        return docterRepository.findAll(pageable); 
    }

    // Get docter by ID
    public Optional<Docter> getDocterById(Long id) {
        return docterRepository.findById(id);
    }

    // Delete docter by ID
    public void deleteDocter(Long id) {
        docterRepository.deleteById(id);
    }
}
