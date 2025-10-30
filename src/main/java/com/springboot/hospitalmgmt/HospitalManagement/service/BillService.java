package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.bill.BillResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillService {
    BillResponseDTO createBill(BillRequestDTO billRequestDTO);
    Page<BillResponseDTO> getAllBills(Pageable pageable);
    Bill getBillById(Long id);
    List<Bill> getBillsByPatientId(Long patientId);
    void deleteBill(Long id);
    BillResponseDTO updateBill(Long id,  BillRequestDTO bill);
}
