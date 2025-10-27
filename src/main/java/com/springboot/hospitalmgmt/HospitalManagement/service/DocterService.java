package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Docter;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface DocterService {
Docter createDocter(Docter docter);
Page<Docter> getAllDocters(int age,int size);
Optional<Docter> getDocterById(Long id);
Docter updateDocter(Long id,Docter docter);
void deleteDocter(Long id);
}
