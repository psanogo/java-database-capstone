// app/src/main/resources/static/js/services/index.js

// Import Required Modules
import { openModal } from '../components/modals.js';
import { API_BASE_URL } from '../config/config.js';

// Define constants for API endpoints
const ADMIN_API = API_BASE_URL + '/admin';
const DOCTOR_API = API_BASE_URL + '/doctor/login';

// Helper function to save the selected role (assuming it's defined elsewhere, e.g., render.js)
// For the purpose of this script, we'll define a placeholder.
// In a real application, you might import this from another module.
function selectRole(role) {
    localStorage.setItem('userRole', role);
    // Potentially redirect or update UI based on role
    // console.log(`Role selected: ${role}`);
    // Example: window.location.href = '/dashboard.html';
}

// Setup Button Event Listeners
window.onload = function () {
    const adminBtn = document.getElementById('adminLogin');
    if (adminBtn) {
        adminBtn.addEventListener('click', () => {
            openModal('adminLogin');
        });
    }

    const doctorBtn = document.getElementById('doctorLogin');
    if (doctorBtn) {
        doctorBtn.addEventListener('click', () => {
            openModal('doctorLogin');
        });
    }
};

// Implement Admin Login Handler
async function adminLoginHandler() {
    try {
        const username = document.getElementById('adminUsername').value;
        const password = document.getElementById('adminPassword').value;

        if (!username || !password) {
            alert("Please enter both username and password for Admin.");
            return;
        }

        const admin = { username, password };

        const response = await fetch(ADMIN_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(admin)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token); // Assuming the token is in data.token
            selectRole("admin");
            alert("Admin login successful!");
            // Optionally close modal or redirect
        } else {
            alert("Invalid credentials!");
        }
    } catch (error) {
        console.error("Error during admin login:", error);
        alert("An unexpected error occurred during admin login. Please try again.");
    }
}

// Implement Doctor Login Handler
async function doctorLoginHandler() {
    try {
        const email = document.getElementById('doctorEmail').value; // Assuming ID for doctor email is doctorEmail
        const password = document.getElementById('doctorPassword').value; // Assuming ID for doctor password is doctorPassword

        if (!email || !password) {
            alert("Please enter both email and password for Doctor.");
            return;
        }

        const doctor = { email, password };

        const response = await fetch(DOCTOR_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(doctor)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token); // Assuming the token is in data.token
            selectRole("doctor");
            alert("Doctor login successful!");
            // Optionally close modal or redirect
        } else {
            alert("Invalid credentials!");
        }
    } catch (error) {
        console.error("Error during doctor login:", error);
        alert("An unexpected error occurred during doctor login. Please try again.");
    }
}

// Make functions globally accessible (as per instructions for a global handler)
// This is typically done if these functions are called directly from HTML onclick attributes
// or if they need to be accessed by other scripts not using modules.
window.adminLoginHandler = adminLoginHandler;
window.doctorLoginHandler = doctorLoginHandler;
