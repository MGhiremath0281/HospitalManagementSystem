package com.springboot.hospitalmgmt.HospitalManagement.repository;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    // Optional: find all bills by patient ID
    List<Bill> findByPatientId(Long patientId);
}
