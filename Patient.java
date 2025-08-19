package com.psanogo.javadatabasecapstone.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Patient entity in the Smart Clinic Portal.
 * This class maps to the 'patients' table and holds personal information
 * about a patient. It is linked to a User account for authentication and
 * has a list of their appointments.
 */
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Size(max = 20)
    @Column(name = "contact_number")
    private String contactNumber;

    // --- Relationships ---

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(
        mappedBy = "patient",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Appointment> appointments = new ArrayList<>();

    // --- Constructors ---

    public Patient() {
    }

    // --- Getters and Setters ---

    // (Standard getters and setters for all fields would go here)

    // Helper method for bidirectional relationship
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setPatient(this);
    }
}

