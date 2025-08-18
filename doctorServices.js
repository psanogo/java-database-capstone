// app/src/main/resources/static/js/services/doctorServices.js

import { API_BASE_URL } from "../config/config.js";

const DOCTOR_API = API_BASE_URL + '/doctor';

/**
 * Fetches all doctors from the API.
 * @returns {Array} An array of doctor objects, or an empty array if an error occurs.
 */
export async function getDoctors() {
    try {
        const response = await fetch(DOCTOR_API);
        if (!response.ok) {
            // Handle HTTP errors
            console.error(`Error fetching doctors: ${response.status} ${response.statusText}`);
            return [];
        }
        const doctors = await response.json();
        return doctors;
    } catch (error) {
        console.error("Network or parsing error while fetching doctors:", error);
        return [];
    }
}

/**
 * Deletes a doctor by their ID.
 * @param {string} id The unique ID of the doctor to delete.
 * @param {string} token The authentication token.
 * @returns {object} An object with success status and a message.
 */
export async function deleteDoctor(id, token) {
    try {
        const response = await fetch(`${DOCTOR_API}/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}` // Assuming Bearer token authentication
            }
        });

        const data = await response.json(); // Even error responses might have a JSON body

        if (response.ok) {
            return { success: true, message: data.message || 'Doctor deleted successfully.' };
        } else {
            console.error(`Error deleting doctor: ${response.status} ${response.statusText}`, data);
            return { success: false, message: data.message || 'Failed to delete doctor.' };
        }
    } catch (error) {
        console.error("Network error while deleting doctor:", error);
        return { success: false, message: 'An unexpected error occurred during deletion.' };
    }
}

/**
 * Saves (adds) a new doctor.
 * @param {object} doctor The doctor object containing details to save.
 * @param {string} token The authentication token.
 * @returns {object} An object with success status and a message.
 */
export async function saveDoctor(doctor, token) {
    try {
        const response = await fetch(DOCTOR_API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(doctor)
        });

        const data = await response.json();

        if (response.ok) {
            return { success: true, message: data.message || 'Doctor added successfully.', doctor: data.doctor }; // Assuming backend returns the saved doctor
        } else {
            console.error(`Error saving doctor: ${response.status} ${response.statusText}`, data);
            return { success: false, message: data.message || 'Failed to add doctor.' };
        }
    } catch (error) {
        console.error("Network error while saving doctor:", error);
        return { success: false, message: 'An unexpected error occurred during saving.' };
    }
}

/**
 * Filters doctors based on provided criteria.
 * @param {string} [name=''] Optional: Doctor's name to filter by.
 * @param {string} [time=''] Optional: Availability time to filter by.
 * @param {string} [specialty=''] Optional: Specialty to filter by.
 * @returns {Array} An array of filtered doctor objects, or an empty array if an error occurs.
 */
export async function filterDoctors(name = '', time = '', specialty = '') {
    try {
        const params = new URLSearchParams();
        if (name) params.append('name', name);
        if (time) params.append('time', time);
        if (specialty) params.append('specialty', specialty);

        const queryString = params.toString();
        const url = queryString ? `${DOCTOR_API}?${queryString}` : DOCTOR_API;

        const response = await fetch(url);

        if (!response.ok) {
            console.error(`Error filtering doctors: ${response.status} ${response.statusText}`);
            return [];
        }
        const doctors = await response.json();
        return doctors;
    } catch (error) {
        console.error("Network or parsing error while filtering doctors:", error);
        return [];
    }
}
