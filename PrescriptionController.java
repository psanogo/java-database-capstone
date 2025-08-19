
package com.psanogo.javadatabasecapstone.controller;

import com.psanogo.javadatabasecapstone.dto.PrescriptionRequest;
import com.psanogo.javadatabasecapstone.model.MedicalRecord;
import com.psanogo.javadatabasecapstone.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for managing prescriptions.
 * In this system, a prescription is part of a MedicalRecord. This controller
 * provides a dedicated endpoint for the action of adding a prescription.
 */
@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    // It's assumed a MedicalRecordService exists to handle the business logic.
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public PrescriptionController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Creates or updates the prescription for a given medical record.
     *
     * @param request The request body containing the medical record ID and prescription text.
     * @return A ResponseEntity containing the updated MedicalRecord and an HTTP 200 (OK) status.
     */
    @PostMapping
    public ResponseEntity<MedicalRecord> addPrescription(@Valid @RequestBody PrescriptionRequest request) {
        MedicalRecord updatedRecord = medicalRecordService.addPrescriptionToRecord(request.getMedicalRecordId(), request.getPrescriptionText());
        return ResponseEntity.ok(updatedRecord);
    }
}

