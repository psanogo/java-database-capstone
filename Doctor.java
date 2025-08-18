package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * Represents a Doctor in the clinic system.
 * This class is a JPA entity mapped to the "doctors" table.
 */
@Entity // Marks this class as a JPA Entity
@Table(name = "doctors") // Specifies the database table name
public class Doctor {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures auto-increment for the ID
    private Long id;

    @NotNull(message = "Doctor name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Specialty cannot be null")
    private String specialty;

    @Email(message = "Email should be valid")
    @NotNull
    @Column(unique = true) // Ensures email is unique in the database
    private String email;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Excludes password from JSON responses for security
    private String password;

    // --- Relationships ---
    // Defines a one-to-many relationship: one doctor can have many appointments.
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments;

    // --- Helper Method (Not persisted) ---
    @Transient // Excludes this method's result from being stored in the database
    public String getDisplayName() {
        return "Dr. " + this.name;
    }

    // --- Getters and Setters ---
    // Note: In a real project, you might use a library like Lombok (@Data, @Getter, @Setter)
    // to automatically generate these and reduce boilerplate code.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
