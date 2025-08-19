import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Finds a patient by their email address.
     * @param email The email to search for.
     * @return An Optional containing the patient if found, otherwise empty.
     */
    Optional<Patient> findByEmail(String email);
}
// In PatientRepository.java
Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber);
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// ...

@Query("SELECT p FROM Patient p WHERE p.email = :email OR p.phoneNumber = :phone")
Optional<Patient> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phoneNumber);
