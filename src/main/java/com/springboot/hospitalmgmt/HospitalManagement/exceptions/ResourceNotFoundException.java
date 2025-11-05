package com.springboot.hospitalmgmt.HospitalManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Standard constructor for when the resource is not found by ID
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s not found with ID : '%s'", resourceName, id));
    }
}