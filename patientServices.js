// app/src/main/resources/static/js/patientDashboard.js

// Import Required Modules
import { createDoctorCard } from '../components/doctorCard.js';
import { openModal } from '../components/modals.js';
import { getDoctors, filterDoctors } from './doctorServices.js';
import { patientLogin, patientSignup } from './patientServices.js'; // Assuming patientServices.js exists

// --- Load Doctor Cards on Page Load ---
document.addEventListener("DOMContentLoaded", () => {
    loadDoctorCards();

    // Bind Modal Triggers for Login and Signup
    const signupBtn = document.getElementById("patientSignup");
    if (signupBtn) {
        signupBtn.addEventListener("click", () => openModal("patientSignup"));
    }

    const loginBtn = document.getElementById("patientLogin");
    if (loginBtn) {
        loginBtn.addEventListener("click", () => openModal("patientLogin"));
    }

    // Search and Filter Logic Listeners
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
});

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

// --- Search and Filter Logic ---
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

// --- Render Utility ---
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

// --- Handle Patient Signup ---
// Make it global so it can be called from HTML form's onsubmit
window.signupPatient = async function () {
    const name = document.getElementById('signupName').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;
    const phone = document.getElementById('signupPhone').value;
    const address = document.getElementById('signupAddress').value;

    const patientData = { name, email, password, phone, address };

    try {
        const result = await patientSignup(patientData); // Call the service function
        if (result.success) {
            alert(result.message);
            // Close modal (assuming openModal handles closing or you have a specific close function)
            // Example: document.getElementById('patientSignupModal').style.display = 'none';
            // Optionally, redirect or refresh
            location.reload(); // Reload page to reflect changes or clear form
        } else {
            alert(result.message);
        }
    } catch (error) {
        console.error("Error during patient signup:", error);
        alert("An unexpected error occurred during signup. Please try again.");
    }
};

// --- Handle Patient Login ---
// Make it global so it can be called from HTML form's onsubmit
window.loginPatient = async function () {
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const result = await patientLogin(email, password); // Call the service function
        if (result.success) {
            localStorage.setItem('token', result.token); // Store JWT token
            alert(result.message);
            window.location.href = 'loggedPatientDashboard.html'; // Redirect to logged-in dashboard
        } else {
            alert(result.message);
        }
    } catch (error) {
        console.error("Error during patient login:", error);
        alert("An unexpected error occurred during login. Please try again.");
    }
};
