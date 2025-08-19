package com.psanogo.javadatabasecapstone.repository;

import com.psanogo.javadatabasecapstone.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Patient entity.
 * This interface provides the mechanism for storage, retrieval, and search
 * behavior for Patient objects.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Spring Data JPA will automatically implement this method based on its name.
    Optional<Patient> findByUserId(Long userId);
}


