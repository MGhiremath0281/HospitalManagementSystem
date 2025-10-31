package com.springboot.hospitalmgmt.HospitalManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "insurance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    // ✅ Automatically set when record is created
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToOne(mappedBy = "insurance", fetch = FetchType.LAZY)
    @JsonBackReference
    private Patient patient;
}
