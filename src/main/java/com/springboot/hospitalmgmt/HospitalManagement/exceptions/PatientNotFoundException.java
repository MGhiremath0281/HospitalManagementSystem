package com.springboot.hospitalmgmt.HospitalManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

    // 1. Constructor for ID (Used in getPatientById, deletePatient if passing ID)
    public PatientNotFoundException(Long id){
        super("Patient not found with ID :" + id);
    }

    // 2. REQUIRED Constructor for String message (Used in updatePatient and general errors)
    public PatientNotFoundException(String message) {
        super(message);
    }

    // You could also add a constructor for chaining exceptions if needed
    // public PatientNotFoundException(String message, Throwable cause) {
    //     super(message, cause);
    // }
}