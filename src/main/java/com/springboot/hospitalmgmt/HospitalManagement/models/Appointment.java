package com.springboot.hospitalmgmt.HospitalManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

// NOTE: Lombok annotations (@Builder, @AllArgsConstructor, @NoArgsConstructor)
// are kept for constructor/builder functionality, but @Getter/@Setter are removed.
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
    private Doctor doctor; // <-- CORRECTED FIELD NAME (Doctor, not Docter)

    @NotBlank(message = "Date can't be blank")
    private String date;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    // --- ID Methods ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // --- DOCTOR Methods (Corrected Spelling and Completed) ---

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