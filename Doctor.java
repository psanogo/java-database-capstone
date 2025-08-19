package com.psanogo.javadatabasecapstone;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Doctor entity in the Smart Clinic Portal.
 * This class maps to the 'doctors' table in the database and holds
 * professional information about a doctor. It is linked to a User account
 * and a list of associated appointments.
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Specialty cannot be blank")
    @Size(max = 100)
    @Column(nullable = false)
    private String specialty;

    @Size(max = 20)
    @Column(name = "contact_number")
    private String contactNumber;

    // --- Relationships ---

    /**
     * Establishes a one-to-one link with the User entity for authentication.
     * The 'user_id' column in the 'doctors' table is a foreign key to the 'users' table.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * Establishes a one-to-many link with the Appointment entity.
     * This is the inverse side of the relationship, managed by the 'doctor' field in the Appointment entity.
     */
    @OneToMany(
        mappedBy = "doctor",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Appointment> appointments = new ArrayList<>();

    // --- Constructors ---

    public Doctor() {
    }

    // --- Getters and Setters ---

    // Note: It's good practice to add helper methods for bidirectional relationships
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setDoctor(this);
    }

    // (Standard getters and setters for all fields would go here)
}

