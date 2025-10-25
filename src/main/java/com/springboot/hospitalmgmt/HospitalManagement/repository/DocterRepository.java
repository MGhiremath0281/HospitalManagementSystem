package com.springboot.hospitalmgmt.HospitalManagement.repository;

import com.springboot.hospitalmgmt.HospitalManagement.models.Docter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocterRepository extends JpaRepository<Docter, Long> {
    // Optional: find by speciality if needed
}
