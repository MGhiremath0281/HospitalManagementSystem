package com.springboot.hospitalmgmt.HospitalManagement.repository;

import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {

}
