package com.springboot.hospitalmgmt.HospitalManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // <-- ADDED
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor association is required.") // <--- ADDED VALIDATION
    private Doctor doctor;

    @NotBlank(message = "Date can't be blank")
    private String date;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient association is required.") // <--- ADDED VALIDATION
    private Patient patient;

    // --- ID Methods ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // --- DOCTOR Methods ---

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    // --- DATE Methods ---

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // --- PATIENT Methods ---

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}