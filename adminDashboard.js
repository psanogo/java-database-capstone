// app/src/main/resources/static/js/adminDashboard.js

// Import Required Modules
import { openModal } from '../components/modals.js';
import { getDoctors, filterDoctors, saveDoctor, deleteDoctor } from './doctorServices.js'; // Added deleteDoctor
import { createDoctorCard } from '../components/doctorCard.js';

// --- Event Binding ---
document.addEventListener('DOMContentLoaded', () => {
    // "Add Doctor" button click handler
    const addDocBtn = document.getElementById('addDocBtn');
    if (addDocBtn) {
        addDocBtn.addEventListener('click', () => {
            openModal('addDoctor'); // Assuming 'addDoctor' is the ID of your add doctor modal
        });
    }

    // Form submission for adding a new doctor
    const addDoctorForm = document.getElementById('addDoctorForm'); // Assuming your form has this ID
    if (addDoctorForm) {
        addDoctorForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // Prevent default form submission
            await adminAddDoctor();
        });
    }

    // Search and Filter Event Listeners
    const searchBar = document.getElementById("searchBar");
    if (searchBar) {
        searchBar.addEventListener("input", filterDoctorsOnChange);
    }

    const filterTime = document.getElementById("filterTime");
    if (filterTime) {
        filterTime.addEventListener("change", filterDoctorsOnChange);
    }

    const filterSpecialty = document.getElementById("filterSpecialty");
    if (filterSpecialty) {
        filterSpecialty.addEventListener("change", filterDoctorsOnChange);
    }

    // Initial load of doctor cards
    loadDoctorCards();
});

// --- Load Doctor Cards on Page Load ---
async function loadDoctorCards() {
    const contentDiv = document.getElementById("content");
    if (!contentDiv) {
        console.error("Content division not found. Cannot load doctor cards.");
        return;
    }
    contentDiv.innerHTML = ""; // Clear existing content

    try {
        const doctors = await getDoctors(); // Fetch all doctors
        if (doctors && doctors.length > 0) {
            renderDoctorCards(doctors);
        } else {
            contentDiv.innerHTML = "<p>No doctors found.</p>";
        }
    } catch (error) {
        console.error("Error loading doctor cards:", error);
        contentDiv.innerHTML = "<p>Failed to load doctors. Please try again later.</p>";
    }
}

// --- Implement Search and Filter Logic ---
async function filterDoctorsOnChange() {
    const searchName = document.getElementById("searchBar")?.value || '';
    const filterTime = document.getElementById("filterTime")?.value || '';
    const filterSpecialty = document.getElementById("filterSpecialty")?.value || '';

    const contentDiv = document.getElementById("content");
    if (!contentDiv) {
        console.error("Content division not found. Cannot filter doctor cards.");
        return;
    }
    contentDiv.innerHTML = ""; // Clear existing content for new results

    try {
        const filtered = await filterDoctors(searchName, filterTime, filterSpecialty);
        if (filtered && filtered.length > 0) {
            renderDoctorCards(filtered);
        } else {
            contentDiv.innerHTML = "<p>No doctors found matching your criteria.</p>";
        }
    } catch (error) {
        console.error("Error filtering doctors:", error);
        contentDiv.innerHTML = "<p>Failed to filter doctors. Please try again later.</p>";
    }
}

// --- Utility function to render doctor cards ---
function renderDoctorCards(doctors) {
    const contentDiv = document.getElementById("content");
    if (!contentDiv) {
        console.error("Content division not found. Cannot render doctor cards.");
        return;
    }
    contentDiv.innerHTML = ""; // Clear existing content before rendering

    doctors.forEach(doctor => {
        const doctorCardHtml = createDoctorCard(doctor);
        contentDiv.insertAdjacentHTML('beforeend', doctorCardHtml);
    });
}

// --- Handle Add Doctor Modal ---
async function adminAddDoctor() {
    const name = document.getElementById('addDoctorName').value;
    const specialty = document.getElementById('addDoctorSpecialty').value;
    const email = document.getElementById('addDoctorEmail').value;
    const password = document.getElementById('addDoctorPassword').value;
    const mobileNo = document.getElementById('addDoctorMobileNo').value;

    // Collect availability checkboxes (example, adjust IDs as needed)
    const availability = [];
    document.querySelectorAll('input[name="addDoctorAvailability"]:checked').forEach((checkbox) => {
        availability.push(checkbox.value);
    });

    const newDoctor = {
        name,
        specialty,
        email,
        password,
        mobileNo,
        availability // This would be an array of strings like ["Monday 09:00-17:00", "Wednesday 10:00-18:00"]
    };

    const token = localStorage.getItem('token'); // Get token from local storage

    if (!token) {
        alert("Authentication token not found. Please log in as Admin.");
        return;
    }

    try {
        const result = await saveDoctor(newDoctor, token);
        if (result.success) {
            alert(result.message);
            // Optionally close the modal if it's not handled by openModal itself
            // For example, if you have a specific modal element:
            // document.getElementById('addDoctorModal').style.display = 'none';
            loadDoctorCards(); // Refresh the list of doctors
            document.getElementById('addDoctorForm').reset(); // Clear the form
        } else {
            alert(result.message);
        }
    } catch (error) {
        console.error("Error adding doctor:", error);
        alert("An unexpected error occurred while adding the doctor.");
    }
}

// --- Optional: Implement Delete Doctor Functionality (if applicable to adminDashboard) ---
// This function would typically be called from within the createDoctorCard or a separate event listener
// attached to a delete button on each doctor card.
window.deleteDoctorHandler = async function(id) { // Make it global for easy access from dynamically created cards
    if (confirm("Are you sure you want to delete this doctor?")) {
        const token = localStorage.getItem('token');
        if (!token) {
            alert("Authentication token not found. Please log in as Admin.");
            return;
        }

        try {
            const result = await deleteDoctor(id, token);
            if (result.success) {
                alert(result.message);
                loadDoctorCards(); // Refresh the list after deletion
            } else {
                alert(result.message);
            }
        } catch (error) {
            console.error("Error deleting doctor:", error);
            alert("An unexpected error occurred during deletion.");
        }
    }
};

// You might also need functions to handle editing doctors,
// which would involve populating an edit modal and calling an updateDoctor service function.
