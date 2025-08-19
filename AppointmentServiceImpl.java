package com.psanogo.javadatabasecapstone.service;

import com.psanogo.javadatabasecapstone.model.Appointment;
import com.psanogo.javadatabasecapstone.model.Doctor;
import com.psanogo.javadatabasecapstone.model.Patient;
import com.psanogo.javadatabasecapstone.repository.AppointmentRepository;
import com.psanogo.javadatabasecapstone.repository.DoctorRepository;
import com.psanogo.javadatabasecapstone.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the AppointmentService interface.
 * This class contains the business logic for appointment management.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  PatientRepository patientRepository,
                                  DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime appointmentDateTime) {
        // Find the patient and doctor, or throw an exception if they don't exist.
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

        Appointment newAppointment = new Appointment();
        newAppointment.setPatient(patient);
        newAppointment.setDoctor(doctor);
        newAppointment.setAppointmentDateTime(appointmentDateTime);

        return appointmentRepository.save(newAppointment);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found with ID: " + doctorId);
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return appointmentRepository.findByDoctorIdAndAppointmentDateTimeBetween(doctorId, startOfDay, endOfDay);
    }
}

