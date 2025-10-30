package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.BillNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import com.springboot.hospitalmgmt.HospitalManagement.repository.BillRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.BillService;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private BillRepository billRepository;

    // Create or update bill
    @Override
    public BillResponseDTO createBill(BillRequestDTO billRequestDTO) {
        logger.info("Creating new bill for patient ID: {}", billRequestDTO.getPatientId());

        Bill bill = modelMapper.map(billRequestDTO,Bill.class);

        Bill savedBill = billRepository.save(bill);
        logger.info("Bill created successfully for patient ID: {}", savedBill.getPatientId());

        // Convert Entity -> Response DTO
        // ERROR FIX: Corrected syntax and mapped the savedBill
        BillResponseDTO responseDTO = modelMapper.map(savedBill, BillResponseDTO.class); 

        return responseDTO;
    }


    // Get all bills
    @Override
    public Page<BillResponseDTO> getAllBills(Pageable pageable) {
        logger.debug("Fetching bills with pagination: {}", pageable); // Added pageable to log for better context

        // 1. Fetch the Paginated Entities
        Page<Bill> bills = billRepository.findAll(pageable);

        // 2. Map the Page<Bill> to Page<BillResponseDTO>
        Page<BillResponseDTO> billDTOS = bills.map(bill -> {
            BillResponseDTO dto =  new BillResponseDTO();

            // ERROR FIX: Populated all expected fields in the DTO
            dto.setId(bill.getId());
            dto.setAmount(bill.getAmount());
            dto.setPatientId(bill.getPatientId()); 
            dto.setStatus(bill.getStatus());       

            // --- Explicitly Return the mapped DTO ---
            return dto;
        });

        return billDTOS;
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

    // Service Layer Implementation
    @Override
    public BillResponseDTO updateBill(Long id, BillRequestDTO dto) {
        logger.info("Updating the bill with the ID : {}", id);

        // 1. Find the existing entity or throw an exception
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Can't update - Bill not found with ID :{}", id);
                    return new BillNotFoundException(id);
                });

        // 2. Apply updates from the DTO to the entity
        // Note: This logic assumes PatientId and Status are fields that can be updated.
        bill.setPatientId(dto.getPatientId());
        bill.setAmount(dto.getAmount());
        bill.setStatus(dto.getStatus()); 

        // 3. Save the updated entity
        Bill updatedBill = billRepository.save(bill);
        logger.info("Bill updated successfully with ID {}", id);

        // 4. Convert the updated Entity to a Response DTO
        // BEST PRACTICE FIX: Use ModelMapper for consistency, though manual mapping was also functionally correct
        BillResponseDTO responseDTO = modelMapper.map(updatedBill, BillResponseDTO.class);
        
        return responseDTO; // Returns the DTO
    }
}