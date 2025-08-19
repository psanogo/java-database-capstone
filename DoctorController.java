package com.psanogo.javadatabasecapstone.controller;

import com.psanogo.javadatabasecapstone.model.Doctor; // Assuming a DTO might be too complex, using the entity directly for simplicity.
import com.psanogo.javadatabasecapstone.service.DoctorService; // Assuming a service layer exists.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing doctors.
 * <p>
 * This controller provides API endpoints for performing CRUD (Create, Read, Update, Delete)
 * operations on Doctor entities. It follows RESTful principles and delegates the
 * business logic to the DoctorService.
 */
@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Retrieves a list of all doctors. This endpoint is typically for admin use.
     *
     * @return A ResponseEntity containing a list of all doctors and an HTTP 200 (OK) status.
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.findAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Retrieves a specific doctor by their ID.
     *
     * @param id The ID of the doctor to retrieve.
     * @return A ResponseEntity containing the doctor if found and HTTP 200 (OK),
     *         or HTTP 404 (Not Found) if the doctor does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        // The service layer would typically return an Optional.
        return doctorService.findDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a doctor by their ID. This is a protected operation for admins.
     *
     * @param id The ID of the doctor to delete.
     * @return A ResponseEntity with HTTP 204 (No Content) on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        // The service should handle the logic of what "deleting" means
        // (e.g., deactivating the account or a hard delete).
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}


