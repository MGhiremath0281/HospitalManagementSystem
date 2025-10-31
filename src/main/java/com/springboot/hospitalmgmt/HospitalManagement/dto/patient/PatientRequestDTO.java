package com.springboot.hospitalmgmt.HospitalManagement.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Getter; // Using @Getter/@Setter for cleaner code
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// Removed all manual getters/setters and replaced with Lombok annotations

@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {
    private String name;
    private String gender;
    private int age;
    private String email;
    private LocalDate admissionDate;

    // 💡 BEST PRACTICE: Use the ID for the relationship, not the full entity object
    private Long insuranceId;

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

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }
}