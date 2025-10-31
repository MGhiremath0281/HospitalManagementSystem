package com.springboot.hospitalmgmt.HospitalManagement.repository;

import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment; // <-- Corrected Import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// The entity name and ID type must be correct
public interface AppointmentRepository extends JpaRepository<Appointment, Long> { // <-- CORRECTED NAME & ENTITY

    // Custom method to find all appointments for a specific patient
    List<Appointment> findByPatient_Id(Long patientId);

    // Custom method to find all appointments for a specific doctor (Fixed 'Docter' spelling)
    List<Appointment> findByDoctor_Id(Long doctorId); // <-- CORRECTED METHOD NAME
}