package com.springboot.hospitalmgmt.HospitalManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Docter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Name can't ne empty")
    @Size(min = 2,max = 30,message = "Enter the name beteween 2 to 30 characters")
    private String name;

    @NotBlank(message = "Specility cant be empty")
    private String spacility;

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

    public String getSpacility() {
        return spacility;
    }

    public void setSpacility(String spacility) {
        this.spacility = spacility;
    }
}
