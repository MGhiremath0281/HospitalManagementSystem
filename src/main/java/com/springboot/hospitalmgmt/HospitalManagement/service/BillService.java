package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BillService {
    Bill saveBill(Bill bill);
    Page<Bill> getAllBills(int page, int size);
    Optional<Bill> getBillById(Long id);
    List<Bill> getBillsByPatientId(Long patientId);
    void deleteBill(Long id);

}
