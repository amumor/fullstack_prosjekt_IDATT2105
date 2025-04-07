// src/services/SearchHistoryService.js

import {
    ApiClient,
    SearchHistoryControllerApi,
    SearchHistoryRequestDTO
} from '@/api';
import {serviceConfigParams} from "@/services/ServiceSetup.js";

const { bearerTokenAuth, timeout, baseURL } = serviceConfigParams();

/**
 * Adds a new search history entry for the user.
 *
 * @param {string} searchQuery - The search query to record.
 * @returns {Promise<Object>} A promise that resolves to the SearchHistoryResponseDTO.
 * @throws {Error} If adding the search history entry fails.
 *
 * @example
 * addSearchHistory('vintage cars')
 *   .then(response => console.log('Search history added:', response))
 *   .catch(error => console.error('Failed to add search history:', error));
 */
export function addSearchHistory(searchQuery) {
    const Client = new ApiClient(baseURL);
    Client.timeout = timeout;
    Client.authenticationRequestDTO = bearerTokenAuth;

    const searchHistoryApi = new SearchHistoryControllerApi(Client);

    const searchHistoryRequestDTO = new SearchHistoryRequestDTO();
    searchHistoryRequestDTO.searchQuery = searchQuery;

    return searchHistoryApi.add(searchHistoryRequestDTO)
        .then(searchHistoryResponseDTO => {
            return searchHistoryResponseDTO;
        })
        .catch(error => {
            console.error('Failed to add search history:', error);
            throw error;
        });
}

/**
 * Retrieves the search history for the currently authenticated user.
 *
 * @returns {Promise<Object>} A promise that resolves to a SearchHistoryListResponseDTO,
 * which contains an array of search queries.
 * @throws {Error} If retrieving the search history fails.
 *
 * @example
 * getSearchHistory()
 *   .then(history => console.log('User search history:', history))
 *   .catch(error => console.error('Failed to retrieve search history:', error));
 */
export function getSearchHistory() {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const searchHistoryApi = new SearchHistoryControllerApi(myClient);

    return searchHistoryApi.findByUserId()
        .then(searchHistoryListResponseDTO => {
            return searchHistoryListResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve search history:', error);
            throw error;
        });
}