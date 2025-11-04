package com.springboot.hospitalmgmt.HospitalManagement.dto.patient;

import java.time.LocalDate;

import com.springboot.hospitalmgmt.HospitalManagement.models.Insurance; // Keep the entity import for now

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {

    private Long id;
    private String name;
    private String gender;
    private int age;
    private String email;
    private LocalDate admissionDate;

    // NOTE: Removed JPA annotations (@OneToOne, @JoinColumn, FetchType.LAZY)
    // DTOs must not contain persistence logic.
    private Insurance insurance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}