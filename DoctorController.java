package com.yourpackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenValidationService tokenValidationService; // Added for validation

    @Autowired
    public DoctorController(DoctorService doctorService, TokenValidationService tokenValidationService) {
        this.doctorService = doctorService;
        this.tokenValidationService = tokenValidationService;
    }

    // ... existing CRUD methods like getAllDoctors, getDoctorById, etc.

    /**
     * Retrieves the availability for a specific doctor on a given date,
     * after validating the provided token.
     *
     * @param user     The user requesting the availability (for logging/auditing).
     * @param doctorId The ID of the doctor.
     * @param date     The date for which to check availability (in YYYY-MM-DD format).
     * @param token    The authentication token for the user.
     * @return A ResponseEntity containing a list of available time slots or an appropriate error status.
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<List<String>> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {

        // 1. First, validate the token. If it's invalid, deny access immediately.
        if (!tokenValidationService.isTokenValid(user, token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }

        // 2. If the token is valid, proceed to get the availability from the service layer.
        Optional<List<String>> availability = doctorService.getDoctorAvailability(doctorId, date);

        // 3. Return the data with a 200 OK, or a 404 Not Found if no availability is found for that date.
        return availability
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


