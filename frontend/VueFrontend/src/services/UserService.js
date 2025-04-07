// src/services/UserService.js

import {
    ApiClient,
    UserControllerApi,
    UserRequestDTO
} from '@/api';
import {serviceConfigParams} from "@/services/ServiceSetup.js";

const { bearerTokenAuth, timeout, baseURL } = serviceConfigParams();

/**
 * Deletes a user by their ID.
 *
 * @param {string} id - The ID of the user to delete.
 * @param {string} token - JWT token
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 * @throws {Error} If deletion fails.
 *
 * @example
 * deleteUser('user123', 'token')
 *   .then(() => console.log('User deleted successfully'))
 *   .catch(error => console.error('Deletion failed:', error));
 */
export function deleteUser(id, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const userApi = new UserControllerApi(client);

    return userApi.deleteUser(id)
        .then(() => {
            // No data is returned on successful deletion. TODO: how to handle 204?
        })
        .catch(error => {
            console.error('Failed to delete user:', error);
            throw error;
        });
}

/**
 * Retrieves a user by their email.
 *
 * @param {string} email - The email of the user.
 * @param {string} token - JWT token
 * @returns {Promise<Object>} A promise that resolves to a UserResponseDTO.
 * @throws {Error} If fetching the user fails.
 *
 * @example
 * getUserByEmail('user@example.com', 'token')
 *   .then(user => console.log('User retrieved:', user))
 *   .catch(error => console.error('Failed to get user:', error));
 */
export function getUserByEmail(email, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const userApi = new UserControllerApi(client);

    return userApi.getUserByEmail(email)
        .then(userResponseDTO => {
            return userResponseDTO;
        })
        .catch(error => {
            console.error('Failed to get user by email:', error);
            throw error;
        });
}

/**
 * Retrieves a user by their ID.
 *
 * @param {string} id - The ID of the user.
 * @param {string} token - JWT token
 * @returns {Promise<Object>} A promise that resolves to a UserResponseDTO.
 * @throws {Error} If fetching the user fails.
 *
 * @example
 * getUserById('user123', 'jwt.tok.en')
 *   .then(user => console.log('User retrieved:', user))
 *   .catch(error => console.error('Failed to get user:', error));
 */
export function getUserById(id, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const userApi = new UserControllerApi(client);

    return userApi.getUserById(id)
        .then(userResponseDTO => {
            return userResponseDTO;
        })
        .catch(error => {
            console.error('Failed to get user by ID:', error);
            throw error;
        });
}

/**
 * Updates the current user's profile with the provided details.
 *
 * @param {Object} user - An object containing the user details to update.
 * @param {string} user.firstName - The first name.
 * @param {string} user.lastName - The last name.
 * @param {string} user.email - The email.
 * @param {string} [user.phoneNumber] - The phone number (optional).
 * @param {string} [user.password] - The new password (optional).
 * @param {string} [user.role] - The role (optional).
 * @param {string} token - JWT token
 * @returns {Promise<Object>} A promise that resolves to the updated UserResponseDTO.
 * @throws {Error} If updating the user fails.
 *
 * @example
 * updateUser({
 *   firstName: 'John',
 *   lastName: 'Doe',
 *   email: 'john@example.com',
 *   phoneNumber: '1234567890',
 *   password: 'newPassword',
 * }, 'jwt.tok.en')
 *   .then(updatedUser => console.log('User updated:', updatedUser))
 *   .catch(error => console.error('Update failed:', error));
 */
export function updateUser(user, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const userApi = new UserControllerApi(client);

    const userRequestDTO = new UserRequestDTO();
    userRequestDTO.firstName = user.firstName;
    userRequestDTO.lastName = user.lastName;
    userRequestDTO.email = user.email;
    userRequestDTO.phoneNumber = user.phoneNumber || null;
    userRequestDTO.password = user.password || null;
    userRequestDTO.role = user.role || null;

    return userApi.updateUser(userRequestDTO)
        .then(userResponseDTO => {
            return userResponseDTO;
        })
        .catch(error => {
            console.error('Failed to update user:', error);
            throw error;
        });
}

/**
 * Retrieves the profile of the currently authenticated user.
 *
 * @param {string} token - JWT token
 * @returns {Promise<Object>} A promise that resolves to a UserResponseDTO containing the user's profile.
 * @throws {Error} If fetching the profile fails.
 *
 * @example
 * getMyProfile()
 *   .then(profile => console.log('My profile:', profile))
 *   .catch(error => console.error('Failed to retrieve my profile:', error));
 */
export function getMyProfile(token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const userApi = new UserControllerApi(client);

    return userApi.getMyProfile()
        .then(userResponseDTO => {
            return userResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve my profile:', error);
            throw error;
        });
}