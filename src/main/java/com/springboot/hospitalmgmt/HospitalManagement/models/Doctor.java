package com.springboot.hospitalmgmt.HospitalManagement.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FIX: Corrected the typo 'ne' to 'be' in the message, as requested.
    @NotBlank(message = "Name can't be empty")
    @Size(min = 2, max = 30, message = "Enter the name between 2 to 30 characters")
    private String name;

    // Retained the custom spelling 'spacility' and its message.
    @NotBlank(message = "Specility cant be empty")
    private String spacility;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // *** FIX FOR ERROR: MappedBy corrected to "doctor" (singular) ***
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointment; // Retained singular name 'appointment'

    // Retained "doctors" as the mappedBy value from Department class
    @ManyToMany(mappedBy = "doctors", cascade = CascadeType.MERGE)
    private Set<Department> departments = new HashSet<>();
    // *** END FIX ***

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
}