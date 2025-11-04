package com.springboot.hospitalmgmt.HospitalManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.hospitalmgmt.HospitalManagement.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByname(String name);

}
