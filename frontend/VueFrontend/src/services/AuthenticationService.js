// src/services/AuthenticationService.js

import {ApiClient, AuthenticationControllerApi, AuthenticationRequestDTO} from '@/api';


export async function authenticateUser(email, password) {
    const myClient = new ApiClient('http://localhost:8080');
    myClient.timeout = 120000; // 2-minute timeout

    const authApi = new AuthenticationControllerApi(myClient);

    const authenticationRequestDTO = new AuthenticationRequestDTO();
    authenticationRequestDTO.email = email;
    authenticationRequestDTO.password = password;

    const response = await authApi.authenticate(authenticationRequestDTO, (err, data, response) => {
        if (err) {
            console.error('Auth failed:', err);
        } else {
            console.log('TokenResponseDTO:', data);
        }
    });
    return response;
}