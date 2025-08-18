package com.project.back_end.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

@Document(collection = "prescriptions") // Marks this as a MongoDB document
public class Prescription {
    
    @Id
    private String id;
    
    private Long patientId; // Reference to the Patient in the relational DB
    private Long doctorId;  // Reference to the Doctor in the relational DB
    
    private String medication;
    private String dosage;
    private String instructions;
    private LocalDate issueDate;
    private int refills;

    // Getters and Setters...
}
