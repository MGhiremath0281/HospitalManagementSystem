package com.springboot.hospitalmgmt.HospitalManagement.dto; // You may need to create this package

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data; // Assuming Lombok is available for simple getters/setters

@Data
public class AppointmentRequest {

    @NotNull(message = "Doctor ID is required for an appointment.")
    private Long doctorId;

    @NotNull(message = "Patient ID is required for an appointment.")
    private Long patientId;

    @NotBlank(message = "Date can't be blank")
    // Note: It's better to use LocalDate and validate the format,
    // but keeping String for consistency with your existing entity.
    private String date;
}