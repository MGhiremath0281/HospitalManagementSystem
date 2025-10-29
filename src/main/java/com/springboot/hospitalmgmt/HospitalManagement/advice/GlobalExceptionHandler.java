package com.springboot.hospitalmgmt.HospitalManagement.advice;

import com.springboot.hospitalmgmt.HospitalManagement.exceptions.AppointmentNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.BillNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles validation errors (HTTP 400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    //Handler for the MALFORMED JSON or INVALID DATA TYPES (HTTP 400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String detail = "Request body is malformed or contains invalid data types.";

        // A common issue is a missing field, which is not easily extracted from this exception.
        // For type mismatch (e.g., sending text for a number), we provide a general message.
        if (ex.getMessage() != null && ex.getMessage().contains("Cannot deserialize value of type")) {
            detail = "Invalid data type provided. Please check field values (e.g., ensure numbers are not strings).";
        }
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request Body",
                detail
        );
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    //Handler for the MISSING REQUIRED QUERY PARAMETERS(HTTP 400) --
    // --- Handler for MISSING REQUIRED QUERY PARAMETERS (HTTP 400) ---
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        String message = String.format("Required query parameter '%s' is missing.", name);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Missing Parameter",
                message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // --- Resource Not Found (HTTP 404) Handlers ---

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFoundException(AppointmentNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBillNotFoundException(BillNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage() // Corrected to match the 3-argument constructor
        );
        return  new ResponseEntity<>(error ,HttpStatus.NOT_FOUND);
    }

    //----Generic Internal Server Error(HTTP 500 Catch -ALL)---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllOtherException(Exception ex){
        //Critical Step - Logging the error
        System.out.println("CRITICAL UNHANDLED SERVER ERROR");
        ex.printStackTrace();


        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occured. Please try agin later or contact support."
        );
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}