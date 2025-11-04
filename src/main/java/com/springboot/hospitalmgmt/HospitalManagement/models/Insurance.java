package com.springboot.hospitalmgmt.HospitalManagement.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "insurance")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Provider can't be blank.")
    @Column(nullable = false, length = 50)
    private String provider;

    @NotBlank(message = "Policy number can't be blank.")
    @Column(nullable = false, unique = true, length = 50)
    private String policyNumber;

    @Positive(message = "Coverage amount must be positive.")
    @Column(nullable = false)
    private Double coverageAmount;

    @NotNull(message = "Valid until date can't be null.")
    @FutureOrPresent(message = "Valid until date cannot be in the past.")
    @Column(nullable = false)
    private LocalDate validUntil;

    // Automatically set when record is created
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Relationship with Patient (bidirectional)
    @OneToOne(mappedBy = "insurance", fetch = FetchType.LAZY)
    @JsonBackReference
    private Patient patient;

    // Explicitly define setter and getter to fix compiler issue
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return this.patient;
    }
}
