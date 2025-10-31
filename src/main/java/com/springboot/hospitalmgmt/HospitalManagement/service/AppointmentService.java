package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.exceptions.AppointmentNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Appointment; // MUST BE 'Appointment'
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {

    Appointment saveAppointment(Appointment appointment); // <-- Check parameter type

    Page<Appointment> getAllAppointments(int page, int size);

    Appointment getAppointmentById(Long id) throws AppointmentNotFoundException;

    // Check naming consistency with your Impl
    List<Appointment> getAppointmentsByPatientId(Long patientId);
    List<Appointment> getAppointmentsByDoctorId(Long doctorId);

    void deleteAppointment(Long id);
}