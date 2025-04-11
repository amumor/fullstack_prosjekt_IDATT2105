// validation.js

// Regex patterns for validations.
const nameRegex = /^[A-Za-zÆØÅæøå]+$/;
const emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
const phoneRegex = /^\d{8}$/;
const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&()_+\-={}:;"'|<>,.?]).{10,}$/;

/**
 * Validates the first name.
 * @param {string} value - The first name input.
 * @returns {boolean} True if valid, otherwise false.
 */
export function validateFirstName(value) {
    if (!value) return false;
    if (!nameRegex.test(value)) return false;
    return true;
}

/**
 * Validates the last name.
 * @param {string} value - The last name input.
 * @returns {boolean} True if valid, otherwise false.
 */
export function validateLastName(value) {
    if (!value) return false;
    if (!nameRegex.test(value)) return false;
    return true;
}

/**
 * Validates the email address.
 * @param {string} value - The email input.
 * @returns {boolean} True if valid, otherwise false.
 */
export function validateEmail(value) {
    if (!value) return false;
    if (!emailRegex.test(value)) return false;
    return true;
}

/**
 * Validates the phone number.
 * @param {string} value - The phone number input.
 * @returns {boolean} True if valid, otherwise false.
 */
export function validatePhoneNumber(value) {
    if (!value) return false;
    if (!phoneRegex.test(value)) return false;
    return true;
}

/**
 * Validates the password.
 * @param {string} value - The password input.
 * @returns {boolean} True if valid, otherwise false.
 */
export function validatePassword(value) {
    if (!value) return false;
    if (!passwordRegex.test(value)) return false;
    return true;
}

/**
 * Validates that the confirm password field matches the password.
 * @param {string} password - The original password.
 * @param {string} confirmPassword - The confirmation password.
 * @returns {boolean} True if both passwords match and confirmPassword exists, otherwise false.
 */
export function validateConfirmPassword(password, confirmPassword) {
    if (!confirmPassword) return false;
    if (password !== confirmPassword) return false;
    return true;
}