package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Insurance;

public interface InsuranceService {

    public void assignInsuranceToPatient(Insurance insurance,Long id);
}
