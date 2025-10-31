package com.springboot.hospitalmgmt.HospitalManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Policy number can't be blank.")
    @Column(nullable = false, unique = true, length = 25)
    private String policyNumber;

    @NotBlank(message = "Provider can't be blank.")
    @Column(nullable = false, length = 50)
    private String provider;

    @NotNull(message = "Valid until date can't be null.")
    @Column(nullable = false)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    // ✅ matches the field name in Patient entity
    @OneToOne(mappedBy = "insurance")
    private Patient patient;
}
