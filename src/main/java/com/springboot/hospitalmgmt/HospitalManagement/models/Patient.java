package com.springboot.hospitalmgmt.HospitalManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email_address", name = "unique_email_constraint")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can't be null or empty.")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    @Column(name = "full_name", nullable = false, length = 30)
    private String name;

    @NotNull(message = "Gender can't be null.")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other.")
    @Column(nullable = false, length = 10)
    private String gender;

    @Min(value = 1, message = "Age must be greater than one.")
    @Max(value = 120, message = "Age can't exceed 120.")
    @Column(nullable = false)
    private int age;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    @Column(name = "email_address", unique = true, nullable = false, length = 100)
    private String email;

    @PastOrPresent(message = "Admission date cannot be in the future.")
    @NotNull(message = "Admission date is required.")
    @Column(nullable = false)
    private LocalDate admissionDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    // Renamed to plural 'appointments' for clarity
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    // --- GETTERS AND SETTERS ---

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

    // FIX: Added accessor methods for the 'appointments' list
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}