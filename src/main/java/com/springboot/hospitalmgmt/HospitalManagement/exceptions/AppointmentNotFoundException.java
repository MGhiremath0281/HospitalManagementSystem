package com.springboot.hospitalmgmt.HospitalManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Tells Spring Boot to respond with HTTP 404 Not Found when this exception is thrown
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppointmentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AppointmentNotFoundException(Long id) {
        super("Appointment not found with ID: " + id);
    }
}