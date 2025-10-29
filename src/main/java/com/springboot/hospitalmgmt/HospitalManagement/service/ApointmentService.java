package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.exceptions.AppointmentNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Apointment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ApointmentService {
    Apointment saveApointment(Apointment apointment);

    Page<Apointment> getAllApointments(int page, int size);

    //changed return type from Optionl<Appontment> to Apointment and added throw clause
    Apointment getApointmentById(Long id) throws AppointmentNotFoundException;

    List<Apointment> getApointmentsByPatientId(Long patientId);

    List<Apointment> getApointmentsByDocterId(Long docterId);

    void deleteApointment(Long id);


}
