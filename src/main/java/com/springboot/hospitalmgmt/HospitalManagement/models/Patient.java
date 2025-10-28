package com.springboot.hospitalmgmt.HospitalManagement.models;

import jakarta.persistence.*; // JPA Annotations
import jakarta.validation.constraints.*; // Bean Validation Annotations
import lombok.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

// JPA Annotation: Enforces a unique constraint on the email column at the DB level
@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email_address", name = "unique_email_constraint")
})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Name Field Validation ---

    // Bean Validation: Ensures it's not null and not just whitespace (Application-level)
    @NotBlank(message = "Name can't be null or empty.")
    // Bean Validation: Enforces length limits (Application-level)
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    // JPA: Enforces NOT NULL and max length at the Database-level
    @Column(name = "full_name", nullable = false, length = 30)
    private String name;

    // --- Gender Field Validation ---

    // Bean Validation: Ensures it's not null (Application-level)
    @NotNull(message = "Gender can't be null.")
    // Bean Validation: Ensures the input matches one of a few allowed values (e.g., Male, Female, Other)
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other.")
    @Column(nullable = false, length = 10)
    private String gender;

    // --- Age Field Validation (Expanded) ---

    // Note: 'int' primitive cannot be null, so @NotNull is effectively validating the box type.
    // Bean Validation: Enforces minimum value (Application-level)
    @Min(value = 1, message = "Age must be greater than one.")
    // Bean Validation: Enforces maximum value (Application-level)
    @Max(value = 120, message = "Age can't exceed 120.")
    @Column(nullable = false)
    private int age;

    // --- New Field: Email (Requires JPA and Bean Validation) ---

    // Bean Validation: Ensures a valid email format (Application-level)
    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    // JPA: Enforces uniqueness and NOT NULL at the Database-level
    @Column(name = "email_address", unique = true, nullable = false, length = 100)
    private String email;

    // --- New Field: Admission Date (Date Validation) ---

    // Bean Validation: Ensures the date is in the past (Hospital admission should be in the past or today)
    @PastOrPresent(message = "Admission date cannot be in the future.")
    @NotNull(message = "Admission date is required.")
    @Column(nullable = false)
    private LocalDate admissionDate;


    // --- Getters and Setters (Updated for new fields) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
}