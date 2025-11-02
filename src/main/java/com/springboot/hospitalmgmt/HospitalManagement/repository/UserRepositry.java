package com.springboot.hospitalmgmt.HospitalManagement.repository;

import com.springboot.hospitalmgmt.HospitalManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositry extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
