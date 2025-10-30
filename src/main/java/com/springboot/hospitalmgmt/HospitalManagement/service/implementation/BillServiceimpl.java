package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillResponseDTO;
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
    public BillResponseDTO createBill(BillRequestDTO billRequestDTO) {
        logger.info("Creating new bill for patient ID: {}", billRequestDTO.getPatientId());

        Bill bill = new Bill();
        bill.setPatientId(billRequestDTO.getPatientId());
        bill.setStatus(billRequestDTO.getStatus());
        bill.setAmount(billRequestDTO.getAmount());

        Bill savedBill = billRepository.save(bill);
        logger.info("Bill created successfully for patient ID: {}", savedBill.getPatientId());

        // Convert Entity → Response DTO
        BillResponseDTO responseDTO = new BillResponseDTO();
        responseDTO.setId(savedBill.getId());
        responseDTO.setAmount(savedBill.getAmount());
        responseDTO.setPatientId(savedBill.getPatientId());
        responseDTO.setStatus(savedBill.getStatus());

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

            // --- Correction: Use Setters to populate the DTO ---
            dto.setId(bill.getId());
            dto.setAmount(bill.getAmount());

            // --- Correction: Explicitly Return the mapped DTO ---
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
        // We only update the mutable fields received from the request DTO.
        // Note: This logic assumes PatientId and Status are fields that can be updated.
        bill.setPatientId(dto.getPatientId());
        bill.setAmount(dto.getAmount());
        bill.setStatus(dto.getStatus()); // Corrected the typo (getStatus -> setStatus)

        // 3. Save the updated entity
        Bill updatedBill = billRepository.save(bill);
        logger.info("Bill updated successfully with ID {}", id);

        // 4. Convert the updated Entity to a Response DTO
        BillResponseDTO responseDTO = new BillResponseDTO();
        responseDTO.setId(updatedBill.getId()); // Include ID in the response
        responseDTO.setAmount(updatedBill.getAmount());
        responseDTO.setPatientId(updatedBill.getPatientId());
        responseDTO.setStatus(updatedBill.getStatus());

        return responseDTO; // Returns the DTO
    }
}
