# User Stories

This document outlines the user stories for the Java Database Capstone project.

---

## Patient Role

### **Title:** Patient Account Registration
*   **As a** new user,
*   **I want to** create a patient account,
*   **so that** I can log in and schedule appointments.
*
*   **Acceptance Criteria:**
    *   Given I am on the registration page,
    *   When I fill in my name, email, date of birth, and a password, and click "Register",
    *   Then a new patient account is created in the system,
    *   And I am automatically logged in and redirected to my personal dashboard.
*   **Priority:** High
*   **Story Points:** 5

### **Title:** View Upcoming Appointments
*   **As a** patient,
*   **I want to** see a list of my scheduled appointments,
*   **so that** I can keep track of when I need to see a doctor.
*
*   **Acceptance Criteria:**
    *   Given I am logged into my patient account,
    *   When I navigate to the "My Appointments" page,
    *   Then I see a list of all my upcoming appointments, including the date, time, and doctor's name for each.
*   **Priority:** High
*   **Story Points:** 3

### **Title:** Schedule a New Appointment
*   **As a** patient,
*   **I want to** schedule a new appointment with a doctor,
*   **so that** I can receive medical care.
*
*   **Acceptance Criteria:**
    *   Given I am logged into my patient account,
    *   When I navigate to the "Schedule Appointment" page,
    *   Then I can select a doctor, view their availability, and choose a date and time slot.
    *   And after confirming the appointment, it appears in my "Upcoming Appointments" list.
*   **Priority:** High
*   **Story Points:** 8

---

## Doctor Role

### **Title:** View Assigned Patients
*   **As a** doctor,
*   **I want to** view a list of all patients assigned to me,
*   **so that** I can manage their care and access their records.
*
*   **Acceptance Criteria:**
    *   Given I am logged into my doctor account,
    *   When I navigate to the "My Patients" dashboard,
    *   Then I see a searchable list of my patients, showing their name and date of birth.
*   **Priority:** High
*   **Story Points:** 5

### **Title:** Access Patient's Medical History
*   **As a** doctor,
*   **I want to** view a patient's complete medical history,
*   **so that** I can make an informed diagnosis and treatment plan.
*
*   **Acceptance Criteria:**
    *   Given I am viewing my list of patients,
    *   When I select a specific patient,
    *   Then I am taken to their profile page where I can view their past appointments, diagnoses, and prescribed medications.
*   **Priority:** High
*   **Story Points:** 8

### **Title:** Update Patient Medical Records
*   **As a** doctor,
*   **I want to** add notes, diagnoses, and prescriptions to a patient's record after an appointment,
*   **so that** their medical history is always up-to-date.
*
*   **Acceptance Criteria:**
    *   Given I am viewing a patient's profile,
    *   When I open their latest appointment record,
    *   Then I can enter and save diagnostic notes, treatment plans, and prescribe medications.
    *   And the new information is saved securely and is visible in the patient's medical history.
*   **Priority:** High
*   **Story Points:** 8

---

## Admin Role

### **Title:** Manage Doctor Accounts
*   **As an** admin,
*   **I want to** be able to add, update, and remove doctor accounts,
*   **so that** I can manage the medical staff using the platform.
*
*   **Acceptance Criteria:**
    *   Given I am logged into my admin account,
    *   When I go to the "Doctor Management" section,
    *   Then I can see a list of all doctors,
    *   And I have options to create a new doctor profile, edit an existing one, or deactivate an account.
*   **Priority:** Medium
*   **Story Points:** 8

### **Title:** Manage Patient Accounts
*   **As an** admin,
*   **I want to** search for, view, and deactivate patient accounts,
*   **so that** I can manage the user base of the portal.
*
*   **Acceptance Criteria:**
    *   Given I am logged into my admin account,
    *   When I go to the "Patient Management" section,
    *   Then I can search for patients by name or email.
    *   And I can view their profile information and have an option to deactivate their account if needed.
*   **Priority:** Medium
*   **Story Points:** 5
