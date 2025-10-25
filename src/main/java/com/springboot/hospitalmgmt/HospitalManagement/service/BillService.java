package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;      
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // Create or update bill
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    // Get all bills
    public Page<Bill> getAllBills(int page,int size) {
        Pageable pagable = PageRequest.of(page,size);
        return billRepository.findAll(pagable);
    }

    // Get bill by ID
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    // Get all bills by patient ID
    public List<Bill> getBillsByPatientId(Long patientId) {
        return billRepository.findByPatientId(patientId);
    }

    // Delete bill by ID
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
}
