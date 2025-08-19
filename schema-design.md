# Database Schema Design

This document details the MySQL database schema for the Smart Clinic Portal. The design is derived from the user stories for Patients, Doctors, and Admins, ensuring all functional requirements are supported by the data model.

## Entities and Relationships

The core entities of the system are:
*   **users**: A central table to handle authentication for all roles (Patient, Doctor, Admin).
*   **patients**: Stores personal information for patients, linked to a user account.
*   **doctors**: Stores professional information for doctors, linked to a user account.
*   **appointments**: Manages the scheduling of appointments between patients and doctors.
*   **medical_records**: Contains the details of a patient's visit, linked to a specific appointment.

### Relationships
*   A `User` can be a `Patient` or a `Doctor` (one-to-one).
*   A `Patient` can have many `Appointments`.
*   A `Doctor` can have many `Appointments`.
*   An `Appointment` has one `Medical_Record`.

## SQL Schema

Below are the `CREATE TABLE` statements for the MySQL database.

### `users` Table
Stores login credentials and role information for all system users.

```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('PATIENT', 'DOCTOR', 'ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### `patients` Table
Stores detailed information about each patient.

```sql
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    contact_number VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### `doctors` Table
Stores detailed information about each doctor.

```sql
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### `appointments` Table
Tracks all scheduled appointments.

```sql
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_datetime DATETIME NOT NULL,
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);
```

### `medical_records` Table
Stores clinical information from an appointment.

```sql
CREATE TABLE medical_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL UNIQUE,
    diagnosis TEXT,
    treatment_plan TEXT,
    prescriptions TEXT,
    notes TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE CASCADE
);
```


