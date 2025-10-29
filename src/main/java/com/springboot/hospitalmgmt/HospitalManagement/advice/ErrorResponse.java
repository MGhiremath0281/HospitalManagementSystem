package com.springboot.hospitalmgmt.HospitalManagement.advice; // Or wherever you put your DTOs

public class ErrorResponse {

    private int status;
    private String error; // e.g., "Not Found"
    private String message; // e.g., "Patient not found with ID: 123"
    private long timestamp;

    // The required 3-argument constructor used by GlobalExceptionHandler
    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // --- Getters (Required for Spring to serialize to JSON) ---

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // --- Setters (Optional, but usually not needed for error DTOs) ---
    /*
    public void setStatus(int status) {
        this.status = status;
    }
    // ...
    */
}