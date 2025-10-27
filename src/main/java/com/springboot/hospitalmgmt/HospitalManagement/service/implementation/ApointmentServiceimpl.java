package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import com.springboot.hospitalmgmt.HospitalManagement.repository.ApointmentRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.ApointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ApointmentServiceimpl implements ApointmentService {

    private static final Logger logger = LoggerFactory.getLogger(ApointmentServiceimpl.class);

    @Autowired
    private ApointmentRepository apointmentRepository;

    @Override
    public Apointment saveApointment(Apointment apointment) {
        if (apointment.getId() == null) {
            logger.info("Creating new appointment for patient ID: {}", apointment.getPatientId());
        } else {
            logger.info("Updating existing appointment with ID: {}", apointment.getId());
        }
        return apointmentRepository.save(apointment);
    }

    @Override
    public Page<Apointment> getAllApointments(int page, int size) {
        logger.debug("Fetching all appointments (page={}, size={})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return apointmentRepository.findAll(pageable);
    }

    @Override
    public Optional<Apointment> getApointmentById(Long id) {
        logger.info("Fetching appointment by ID: {}", id);
        return apointmentRepository.findById(id);
    }

    @Override
    public List<Apointment> getApointmentsByPatientId(Long patientId) {
        logger.info("Fetching all appointments for patient ID: {}", patientId);
        return apointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Apointment> getApointmentsByDocterId(Long docterId) {
        logger.info("Fetching all appointments for doctor ID: {}", docterId);
        return apointmentRepository.findByDocterId(docterId);
    }

    @Override
    public void deleteApointment(Long id) {
        logger.warn("Deleting appointment with ID: {}", id);
        apointmentRepository.deleteById(id);
    }

}
