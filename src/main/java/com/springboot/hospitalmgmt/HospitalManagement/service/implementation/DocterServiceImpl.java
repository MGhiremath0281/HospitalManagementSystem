package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.repository.DocterRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.DocterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Service
public class DocterServiceImpl implements DocterService {

    private static final Logger logger = LoggerFactory.getLogger(DocterServiceImpl.class);

    @Autowired
    private DocterRepository docterRepository;

    @Override
    public Doctor createDocter(Doctor doctor) {
        logger.info("Creating doctor with ID: {}", doctor.getId());
        return docterRepository.save(doctor);
    }

    @Override
    public Page<Doctor> getAllDocters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.debug("Fetching doctors with pagination: page={}, size={}", page, size);
        return docterRepository.findAll(pageable);
    }

    @Override
    public Optional<Doctor> getDocterById(Long id) {
        logger.debug("Fetching doctor with ID: {}", id);
        return docterRepository.findById(id);
    }

    @Override
    public Doctor updateDocter(Long id, Doctor doctor) {
        logger.info("Updating doctor with ID: {}", id);

        return docterRepository.findById(id).map(existingDocter -> {
            existingDocter.setName(doctor.getName());
            existingDocter.setSpacility(doctor.getSpacility());
            return docterRepository.save(existingDocter);
        }).orElse(null);
    }

    @Override
    public void deleteDocter(Long id) {
        logger.info("Deleting doctor with ID: {}", id);
        docterRepository.deleteById(id);
    }
}
