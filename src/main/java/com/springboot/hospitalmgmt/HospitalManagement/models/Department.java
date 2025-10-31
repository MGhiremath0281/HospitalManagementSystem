package com.springboot.hospitalmgmt.HospitalManagement.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 100)
    private String name;

    // *** DEPARTMENT STUFF ***
    @OneToOne
    @JoinColumn(nullable = false, name = "head_doctor_id") // Using 'name' for clarity
    private Doctor headDoctor;

    @ManyToMany // No 'mappedBy' as this is the owning side
    @JoinTable(
            name = "my_dpt_docters",
            joinColumns = @JoinColumn(name = "dpt_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
            // Refers to the Doctor ID
    )
    private Set<Doctor> doctors = new HashSet<>(); // Field name "doctors" matches mappedBy in Doctor class
    // *** END DEPARTMENT STUFF ***
}