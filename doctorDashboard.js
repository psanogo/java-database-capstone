// app/src/main/resources/static/js/doctorDashboard.js

// Import Required Modules
import { getAllAppointments } from './appointmentRecordService.js';
import { createPatientRow } from '../components/patientRows.js';

// --- Initialize Global Variables ---
const patientTableBody = document.getElementById('patientTableBody');
let selectedDate = new Date().toISOString().split('T')[0]; // YYYY-MM-DD format for today
const token = localStorage.getItem('token'); // Retrieve token from localStorage
let patientName = null; // For search filtering

// --- Setup Search Bar Functionality ---
document.addEventListener('DOMContentLoaded', () => {
    const searchBar = document.getElementById('searchBar');
    if (searchBar) {
        searchBar.addEventListener('input', (event) => {
            patientName = event.target.value.trim();
            if (patientName === "") {
                patientName = null; // Reset to null if search input is empty
            }
            loadAppointments(); // Refresh the list with filtered data
        });
    }

    // --- Bind Event Listeners to Filter Controls ---
    const todayButton = document.getElementById('todayButton');
    if (todayButton) {
        todayButton.addEventListener('click', () => {
            selectedDate = new Date().toISOString().split('T')[0]; // Reset to today's date
            const datePicker = document.getElementById('datePicker');
            if (datePicker) {
                datePicker.value = selectedDate; // Update date picker field
            }
            loadAppointments();
        });
    }

    const datePicker = document.getElementById('datePicker');
    if (datePicker) {
        datePicker.value = selectedDate; // Set initial value to today
        datePicker.addEventListener('change', (event) => {
            selectedDate = event.target.value; // Update selectedDate
            loadAppointments(); // Fetch and display appointments for the selected date
        });
    }

    // --- Initial Render on Page Load ---
    loadAppointments(); // Load today’s appointments by default
});


// --- Define loadAppointments() Function ---
async function loadAppointments() {
    if (!patientTableBody) {
        console.error("Appointment table body (#patientTableBody) not found.");
        return;
    }

    patientTableBody.innerHTML = ''; // Clear existing content

    // Display loading message
    patientTableBody.innerHTML = `<tr><td colspan="6" style="text-align: center;">Loading appointments...</td></tr>`;

    if (!token) {
        patientTableBody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: red;">Authentication token missing. Please log in.</td></tr>`;
        console.error("Authentication token not found in localStorage.");
        return;
    }

    try {
        const appointments = await getAllAppointments(selectedDate, patientName, token);

        if (appointments && appointments.length > 0) {
            appointments.forEach(appointment => {
                // Assuming appointment object has patient details directly or nested
                // Adjust this part based on your actual appointment data structure
                const patient = appointment.patient; // Example: if patient details are nested
                const rowHtml = createPatientRow(patient, appointment); // Pass both patient and appointment details if needed by createPatientRow
                patientTableBody.insertAdjacentHTML('beforeend', rowHtml);
            });
        } else {
            patientTableBody.innerHTML = `<tr><td colspan="6" style="text-align: center;">No appointments found for ${selectedDate}${patientName ? ` for patient "${patientName}"` : ''}.</td></tr>`;
        }
    } catch (error) {
        console.error("Error loading appointments:", error);
        patientTableBody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: red;">Failed to load appointments. Please try again.</td></tr>`;
    }
}

// Export functions if they need to be called from other modules (e.g., for testing)
// export { loadAppointments }; // Uncomment if needed
