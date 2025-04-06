// src/services/AuthenticationService.js

import { AuthenticationControllerApi, Configuration, AuthenticationRequestDTO } from '@/api';

export async function authenticateUser(email, password) {
    const apiClient = new AuthenticationControllerApi(
        new Configuration({ basePath: 'http://localhost:8080' }),
    );
    const authenticationRequestDTO = new AuthenticationRequestDTO();
    authenticationRequestDTO.email = email;
    authenticationRequestDTO.password = password;

    try {
        const response = await apiClient.authenticate(authenticationRequestDTO);
        console.log("API called successfully. Returned data:", response);
        return response;
    } catch (error) {
        console.error(error);
        throw error;
    }
}