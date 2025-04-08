// src/services/AuthenticationService.js

import {
    ApiClient,
    AuthenticationControllerApi,
    AuthenticationRequestDTO,
    UserRequestDTO
} from '@/api';
import { serviceConfigParams } from '@/services/ServiceSetup.js';

const { timeout, baseURL } = serviceConfigParams();

/**
 * Authenticates a user with the given email and password.
 *
 * @param {string} email - The email of the user.
 * @param {string} password - The password of the user.
 * @returns {Promise<Object>} A promise that resolves to the token response.
 * @throws {Error} If authentication fails.
 *
 * @example
 * authenticateUser('user@example.com', 'password')
 *   .then(response => {
 *     console.log('User authenticated successfully:', response);
 *   })
 *   .catch(error => {
 *     console.error('Authentication failed:', error);
 *   });
 */
export function authenticateUser(email, password) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;

    const authApi = new AuthenticationControllerApi(client);

    const authenticationRequestDTO = new AuthenticationRequestDTO();
    authenticationRequestDTO.email = email;
    authenticationRequestDTO.password = password;

    return authApi.authenticate(authenticationRequestDTO)
        .then(tokenResponseDTO => tokenResponseDTO)
        .catch(error => {
            console.error('Auth failed:', error);
            throw error;
        });
}

/**
 * Registers a new user with the given user details.
 *
 * @param {Object} user - An object containing user details.
 * @param {string} user.firstName - The first name.
 * @param {string} user.lastName - The last name.
 * @param {string} user.email - The email.
 * @param {string} user.password - The password.
 * @param {string} [user.phoneNumber] - The phone number (optional).
 * @returns {Promise<Object>} A promise that resolves to the token response.
 * @throws {Error} If registration fails.
 *
 * @example
 * registerUser({
 *   firstName: 'Jane',
 *   lastName: 'Doe',
 *   email: 'janedoe@example.com',
 *   password: 'secret123',
 *   phoneNumber: '55555555',
 * })
 *   .then(response => console.log('User registered successfully:', response))
 *   .catch(error => console.error('Registration failed:', error));
 */
export function registerUser(user) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;

    const authApi = new AuthenticationControllerApi(client);

    const userRequestDTO = new UserRequestDTO();
    userRequestDTO.firstName = user.firstName;
    userRequestDTO.lastName = user.lastName;
    userRequestDTO.email = user.email;
    userRequestDTO.password = user.password;
    userRequestDTO.phoneNumber = user.phoneNumber || null;
    userRequestDTO.role = UserRequestDTO.RoleEnum.ROLE_USER;

    return authApi.register(userRequestDTO)
        .then(tokenResponseDTO => tokenResponseDTO)
        .catch(error => {
            console.error('User registration failed:', error);
            throw error;
        });
}

/**
 * Registers a new admin user with the given user details.
 *
 * @param {Object} user - An object containing user details.
 * @param {string} user.firstName - The first name.
 * @param {string} user.lastName - The last name.
 * @param {string} user.email - The email.
 * @param {string} user.password - The password.
 * @param {string} [user.phoneNumber] - The phone number (optional).
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the token response.
 * @throws {Error} If admin registration fails.
 *
 * @example
 * registerAdminUser({
 *   firstName: 'Admin',
 *   lastName: 'Smith',
 *   email: 'admin@example.com',
 *   password: 'adminSecret',
 *   phoneNumber: '55555555'
 * }, 'jwt.tok.en')
 *   .then(response => console.log('Admin registered successfully:', response))
 *   .catch(error => console.error('Admin registration failed:', error));
 */
export function registerAdminUser(user, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const authApi = new AuthenticationControllerApi(client);

    const userRequestDTO = new UserRequestDTO();
    userRequestDTO.firstName = user.firstName;
    userRequestDTO.lastName = user.lastName;
    userRequestDTO.email = user.email;
    userRequestDTO.password = user.password;
    userRequestDTO.phoneNumber = user.phoneNumber || null;
    userRequestDTO.role = UserRequestDTO.RoleEnum.ROLE_ADMIN;

    return authApi.registerAdmin(userRequestDTO)
        .then(tokenResponseDTO => tokenResponseDTO)
        .catch(error => {
            console.error('Admin registration failed:', error);
            throw error;
        });
}