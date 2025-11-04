package com.springboot.hospitalmgmt.HospitalManagement.dto.bill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BillRequestDTO {

    @NotNull(message = "Patient id can't be empty")
    private Long patientId;

    @NotNull(message = "Amount can't be blank")
    private double amount;

    @NotBlank(message = "Status can't be blank")
    private String status;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
