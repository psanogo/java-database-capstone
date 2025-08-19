package com.psanogo.javadatabasecapstone.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A Data Transfer Object (DTO) for handling incoming prescription requests.
 * This class structures the data required to add a prescription to a medical record.
 */
public class PrescriptionRequest {

    @NotNull(message = "Medical Record ID cannot be null")
    private Long medicalRecordId;

    @NotBlank(message = "Prescription text cannot be blank")
    private String prescriptionText;

    // Getters and Setters
    public Long getMedicalRecordId() {
        return medicalRecordId;
    }

    public String getPrescriptionText() {
        return prescriptionText;
    }
}

