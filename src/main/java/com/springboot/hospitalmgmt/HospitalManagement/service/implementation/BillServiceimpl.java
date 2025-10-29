package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.exceptions.BillNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.repository.BillRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BillServiceimpl implements BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillServiceimpl.class);

    @Autowired
    private BillRepository billRepository;

    // Create or update bill
    @Override
    public Bill createBill(Bill bill) {
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
    public Bill getBillById(Long id) {
        logger.info("Fetching bill by ID: {}", id);
        return billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException(id));
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
       Bill existingBill = billRepository.findById(id)
                       .orElseThrow(() ->{
                           logger.error("can't delete - Bill not found with ID: {}",id);
                           return new BillNotFoundException(id);
                       });
        billRepository.deleteById(id);
        logger.info("Sucessfully deleted bill with ID : {}",id);

    }

    @Override
    public Bill updateBill(Long id, Bill bill) {
        logger.info("Updating the bill with the ID : {}",id);
        return billRepository.findById(id)
                .map(existingBill ->{
                    existingBill.setPatientId(bill.getPatientId());
                    existingBill.setAmount(bill.getAmount());
                    existingBill.setStatus(bill.getStatus());
                    logger.debug("Updated the the bill for the ID : {}",id);
                    return billRepository.save(existingBill);
                })
                .orElseThrow(() ->{
                    logger.error("Can't update - Bill not found with ID :{}",id);
                    return new BillNotFoundException(id);
                });

    }
}
