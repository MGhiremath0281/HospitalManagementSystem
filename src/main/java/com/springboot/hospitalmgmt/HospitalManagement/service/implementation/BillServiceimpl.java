package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.repository.BillRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BillServiceimpl implements BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillServiceimpl.class);

    @Autowired
    private BillRepository billRepository;

    // Create or update bill
    @Override
    public Bill saveBill(Bill bill) {
        logger.info("Creating/Updating the bill with id: {}", bill.getId());
        return billRepository.save(bill);
    }

    // Get all bills
    @Override
    public Page<Bill> getAllBills(int page, int size) {
        logger.debug("Fetching bills with pagination: page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return billRepository.findAll(pageable);
    }

    // Get bill by ID
    @Override
    public Optional<Bill> getBillById(Long id) {
        logger.info("Fetching bill by id: {}", id);
        return billRepository.findById(id);
    }

    // Get bills by patient ID
    @Override
    public List<Bill> getBillsByPatientId(Long patientId) {
        logger.info("Fetching all bills for patientId: {}", patientId);
        return billRepository.findByPatientId(patientId);
    }

    // Delete bill
    @Override
    public void deleteBill(Long id) {
        logger.info("Deleting bill with id: {}", id);
        billRepository.deleteById(id);
    }
}
