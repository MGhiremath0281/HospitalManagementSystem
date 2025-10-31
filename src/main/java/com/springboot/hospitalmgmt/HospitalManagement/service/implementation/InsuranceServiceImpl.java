package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.models.Insurance;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.InsuranceService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final PatientRepository patientRepository;

    public InsuranceServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    @Builder
    public void assignInsuranceToPatient(Insurance insurance, Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));

        // Link both sides of the relationship
        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        // Save updated patient (and possibly cascade insurance)
        patientRepository.save(patient);
    }
}
