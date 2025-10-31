package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.exceptions.AppointmentNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment; // Corrected Import
import com.springboot.hospitalmgmt.HospitalManagement.repository.AppointmentRepository; // Renamed Repository (assuming you updated it)
import com.springboot.hospitalmgmt.HospitalManagement.service.AppointmentService; // Corrected Import
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService { // Corrected Class Name and Interface

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private AppointmentRepository appointmentRepository; // Corrected Repository Name

    @Override
    public Appointment saveAppointment(Appointment appointment) { // Corrected Method Name
        if (appointment.getId() == null) {
            // Checks for Patient ID: Note that 'getPatient()' and 'getId()' work because your Patient entity uses @Getter
            Long patientId = (appointment.getPatient() != null) ? appointment.getPatient().getId() : null;
            logger.info("Creating new appointment for patient ID: {}", patientId);
        } else {
            logger.info("Updating existing appointment with ID: {}", appointment.getId());
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public Page<Appointment> getAllAppointments(int page, int size) { // Corrected Method Name
        logger.debug("Fetching all appointments (page={}, size={})", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return appointmentRepository.findAll(pageable);
    }

    @Override
    public Appointment getAppointmentById(Long id) throws AppointmentNotFoundException { // Corrected Method Name
        logger.info("Fetching appointment by ID: {}", id);
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) { // Corrected Method Name
        logger.info("Fetching all appointments for patient ID: {}", patientId);
        // Uses findByPatient_Id (assuming this custom query method is in your Repository)
        return appointmentRepository.findByPatient_Id(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) { // Corrected Method Name and Spelling
        logger.info("Fetching all appointments for doctor ID: {}", doctorId);
        // Uses findByDoctor_Id (assuming this custom query method is in your Repository)
        return appointmentRepository.findByDoctor_Id(doctorId);
    }

    @Override
    public void deleteAppointment(Long id) { // Corrected Method Name
        logger.warn("Deleting appointment with ID: {}", id);
        appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Can't delete - Appointment with ID: {} not found", id);
                    return new AppointmentNotFoundException(id);
                });
        appointmentRepository.deleteById(id);
        logger.info("Successfully deleted the appointment with ID: {}", id);
    }
}