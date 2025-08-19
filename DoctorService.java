package com.example.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// In a real project, Doctor and AvailabilitySlot would be in their own files (e.g., in a 'model' or 'domain' package).

/**
 * Represents a doctor in the system.
 */
class Doctor {
    private final long id;
    private final String username;
    private final String passwordHash; // IMPORTANT: In a real app, this would be a BCrypt hash.
    private final String firstName;
    private final String lastName;

    public Doctor(long id, String username, String passwordHash, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters
    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}

/**
 * Represents a time slot for a doctor's availability.
 */
class AvailabilitySlot {
    private final long id;
    private final long doctorId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final boolean isBooked;

    public AvailabilitySlot(long id, long doctorId, LocalDateTime startTime, LocalDateTime endTime, boolean isBooked) {
        this.id = id;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = isBooked;
    }

    // Getters
    public long getId() { return id; }
    public long getDoctorId() { return doctorId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isBooked() { return isBooked; }
}

/**
 * Defines the contract for data access operations related to doctors.
 * In a real project, this would be an interface in a 'repository' package.
 */
interface DoctorRepository {
    Optional<Doctor> findByUsername(String username);
    List<AvailabilitySlot> findAvailabilityByDoctorId(long doctorId);
}

/**
 * A mock repository for demonstration. In a real app, this would use JPA,
 * JDBC, or another data access technology to connect to a database.
 */
class MockDoctorRepository implements DoctorRepository {
    private final Map<String, Doctor> doctorsByUsername = new HashMap<>();
    private final List<AvailabilitySlot> availabilitySlots = new ArrayList<>();

    public MockDoctorRepository() {
        // --- Seed with sample data ---
        // NOTE: Storing plain text passwords is a major security risk.
        // This is for demonstration only. Use a password encoder like BCrypt.
        Doctor drJones = new Doctor(1L, "drjones", "password123", "Indiana", "Jones");
        Doctor drHouse = new Doctor(2L, "drhouse", "vicodin", "Gregory", "House");

        doctorsByUsername.put(drJones.getUsername(), drJones);
        doctorsByUsername.put(drHouse.getUsername(), drHouse);

        // Availability for Dr. Jones
        availabilitySlots.add(new AvailabilitySlot(101L, 1L, LocalDateTime.now().plusDays(1).withHour(9), LocalDateTime.now().plusDays(1).withHour(10), false));
        availabilitySlots.add(new AvailabilitySlot(102L, 1L, LocalDateTime.now().plusDays(1).withHour(10), LocalDateTime.now().plusDays(1).withHour(11), true)); // Booked
        availabilitySlots.add(new AvailabilitySlot(103L, 1L, LocalDateTime.now().plusDays(1).withHour(11), LocalDateTime.now().plusDays(1).withHour(12), false));

        // Availability for Dr. House
        availabilitySlots.add(new AvailabilitySlot(201L, 2L, LocalDateTime.now().plusDays(2).withHour(14), LocalDateTime.now().plusDays(2).withHour(15), false));
    }

    @Override
    public Optional<Doctor> findByUsername(String username) {
        return Optional.ofNullable(doctorsByUsername.get(username));
    }

    @Override
    public List<AvailabilitySlot> findAvailabilityByDoctorId(long doctorId) {
        return availabilitySlots.stream()
                .filter(slot -> slot.getDoctorId() == doctorId)
                .collect(Collectors.toList());
    }
}

/**
 * Service layer for handling business logic related to doctors.
 * This includes validating credentials and retrieving availability.
 */
public class DoctorService {

    private final DoctorRepository doctorRepository;

    /**
     * Constructs a DoctorService with a data repository.
     * This uses dependency injection, a key best practice.
     * @param doctorRepository The repository for accessing doctor data.
     */
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Validates a doctor's login credentials.
     *
     * @param username The doctor's username.
     * @param password The doctor's password.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean validateLogin(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        Optional<Doctor> doctorOpt = doctorRepository.findByUsername(username);

        // In a real application, you would use a secure password encoder.
        // Example: return doctorOpt.map(doctor -> passwordEncoder.matches(password, doctor.getPasswordHash())).orElse(false);
        return doctorOpt.map(doctor -> doctor.getPasswordHash().equals(password)).orElse(false);
    }

    /**
     * Retrieves all available (not booked) time slots for a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of available time slots.
     */
    public List<AvailabilitySlot> getDoctorAvailability(long doctorId) {
        return doctorRepository.findAvailabilityByDoctorId(doctorId)
                .stream()
                .filter(slot -> !slot.isBooked()) // Filter out already booked slots
                .collect(Collectors.toList());
    }

    /**
     * Main method to demonstrate the service's functionality.
     */
    public static void main(String[] args) {
        // 1. Set up the repository and service
        DoctorRepository repository = new MockDoctorRepository();
        DoctorService doctorService = new DoctorService(repository);

        // 2. Test Login Validation
        System.out.println("--- Testing Login ---");
        System.out.println("Login for 'drjones' with correct password: " + doctorService.validateLogin("drjones", "password123")); // Should be true
        System.out.println("Login for 'drjones' with incorrect password: " + doctorService.validateLogin("drjones", "wrongpass")); // Should be false
        System.out.println("Login for non-existent user 'drwho': " + doctorService.validateLogin("drwho", "tardis")); // Should be false

        // 3. Test Availability Retrieval
        System.out.println("\n--- Testing Availability ---");
        long drJonesId = 1L;
        List<AvailabilitySlot> jonesAvailability = doctorService.getDoctorAvailability(drJonesId);
        System.out.println("Available slots for Dr. Jones (ID: " + drJonesId + "):");
        jonesAvailability.forEach(slot ->
            System.out.println("  - From " + slot.getStartTime() + " to " + slot.getEndTime())
        );
    }
}

