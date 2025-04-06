// src/services/AuthenticationService.js
import { ApiClient, AuthenticationControllerApi, AuthenticationRequestDTO } from '@/api';

/**
 * Authenticates a user with the given email and password.
 *
 * @param {string} email - The email of the user.
 * @param {string} password - The password of the user.
 * @returns {Promise<Object>} A promise that resolves to the authentication response.
 * @throws {Error} If authentication fails.
 *
 * @example
 * // Example usage:
 * authenticateUser('user@example.com', 'password')
 *   .then(response => {
 *     console.log('User authenticated successfully:', response);
 *   })
 *   .catch(error => {
 *     console.error('Authentication failed:', error);
 *   });
 */
export function authenticateUser(email, password) {
    const myClient = new ApiClient('http://localhost:8080');
    myClient.timeout = 120000; // 2-minute timeout

    const authApi = new AuthenticationControllerApi(myClient);

    const authenticationRequestDTO = new AuthenticationRequestDTO();
    authenticationRequestDTO.email = email;
    authenticationRequestDTO.password = password;

    return authApi.authenticate(authenticationRequestDTO)
        .then(tokenResponseDTO => {
            // console.log('TokenResponseDTO:', tokenResponseDTO);
            return tokenResponseDTO;
        })
        .catch(error => {
            console.error('Auth failed:', error);
            throw error;
        });
}