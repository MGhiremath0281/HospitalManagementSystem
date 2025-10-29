package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {
    Bill createBill(Bill bill);
    Page<Bill> getAllBills(int page, int size);
    Bill getBillById(Long id);
    List<Bill> getBillsByPatientId(Long patientId);
    void deleteBill(Long id);
    Bill updateBill(Long id,  Bill bill);
}
