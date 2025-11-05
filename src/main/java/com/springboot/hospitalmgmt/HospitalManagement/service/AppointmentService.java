package com.springboot.hospitalmgmt.HospitalManagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.hospitalmgmt.HospitalManagement.dto.AppointmentRequest; // <-- NEW IMPORT
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.AppointmentNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment;

public interface AppointmentService {

    // Method signature changed to accept the DTO
    Appointment saveAppointment(AppointmentRequest request); // <--- CHANGED PARAMETER

    Page<Appointment> getAllAppointments(int page, int size);

    Appointment getAppointmentById(Long id) throws AppointmentNotFoundException;

    List<Appointment> getAppointmentsByPatientId(Long patientId);

    List<Appointment> getAppointmentsByDoctorId(Long doctorId);

    void deleteAppointment(Long id);
}