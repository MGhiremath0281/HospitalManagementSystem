package com.springboot.hospitalmgmt.HospitalManagement.repository;

import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApointmentRepository extends JpaRepository<Apointment, Long> {
    // Optional: get all appointments for a specific patient
    List<Apointment> findByPatientId(Long patientId);

    // Optional: get all appointments for a specific doctor
    List<Apointment> findByDocterId(Long docterId);
}
