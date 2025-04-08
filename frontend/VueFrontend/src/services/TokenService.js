// src/services/TokenService.js
import { jwtDecode } from 'jwt-decode';

/**
 * Checks whether a JWT token is expired.
 *
 * @param {string} token - The JWT token.
 * @returns {boolean} True if the token is expired or invalid, otherwise false.
 * @example
 *     const token = userStorage.token;
 *     if (isTokenExpired(token)) {
 *       userStorage.logout();
 *     }
 */
export function isTokenExpired(token) {
    if (!token) {
        console.error('No token given as parameter');
        return true;
    }
    const expirationDate = getTokenExpirationDate(token);
    if (!expirationDate) {
        console.error('Expiration date is invalid');
        return true;
    }
    return expirationDate < new Date();
}

/**
 * Extracts the expiration date from a JWT token.
 *
 * @param {string} token - The JWT token.
 * @returns {Date|null} The expiration date as a JavaScript Date object, or null if not defined.
 */
export function getTokenExpirationDate(token) {
    try {
        const decoded = jwtDecode(token);
        if (!decoded.exp) {
            return null;
        }
        const date = new Date(0); // Epoch time
        date.setUTCSeconds(decoded.exp);
        return date;
    } catch (error) {
        console.error('Failed to decode token:', error);
        return null;
    }
}