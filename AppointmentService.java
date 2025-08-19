package com.psanogo.javadatabasecapstone.service;

import com.psanogo.javadatabasecapstone.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer interface for managing appointments.
 * Defines the business logic operations related to appointments, abstracting
 * the data access layer.
 */
public interface AppointmentService {

    /**
     * Books a new appointment for a patient with a specific doctor.
     */
    Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime appointmentDateTime);

    /**
     * Retrieves all appointments for a given doctor on a specific date.
     */
    List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date);
}


