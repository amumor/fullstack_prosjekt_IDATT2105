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
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 * @throws {Error} If deletion fails.
 *
 * @example
 * deleteUser('user123')
 *   .then(() => console.log('User deleted successfully'))
 *   .catch(error => console.error('Deletion failed:', error));
 */
export function deleteUser(id) {
    const Client = new ApiClient(baseURL);
    Client.timeout = timeout;
    Client.authenticationRequestDTO = bearerTokenAuth;

    const userApi = new UserControllerApi(Client);

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
 * @returns {Promise<Object>} A promise that resolves to a UserResponseDTO.
 * @throws {Error} If fetching the user fails.
 *
 * @example
 * getUserByEmail('user@example.com')
 *   .then(user => console.log('User retrieved:', user))
 *   .catch(error => console.error('Failed to get user:', error));
 */
export function getUserByEmail(email) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const userApi = new UserControllerApi(myClient);

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
 * @returns {Promise<Object>} A promise that resolves to a UserResponseDTO.
 * @throws {Error} If fetching the user fails.
 *
 * @example
 * getUserById('user123')
 *   .then(user => console.log('User retrieved:', user))
 *   .catch(error => console.error('Failed to get user:', error));
 */
export function getUserById(id) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const userApi = new UserControllerApi(myClient);

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
 * @returns {Promise<Object>} A promise that resolves to the updated UserResponseDTO.
 * @throws {Error} If updating the user fails.
 *
 * @example
 * updateUser({
 *   firstName: 'John',
 *   lastName: 'Doe',
 *   email: 'john@example.com',
 *   phoneNumber: '1234567890',
 *   password: 'newPassword'
 * })
 *   .then(updatedUser => console.log('User updated:', updatedUser))
 *   .catch(error => console.error('Update failed:', error));
 */
export function updateUser(user) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const userApi = new UserControllerApi(myClient);

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