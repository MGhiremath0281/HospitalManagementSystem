package com.springboot.hospitalmgmt.HospitalManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id){
        super("Patient not found with ID :" + id);
    }
}
