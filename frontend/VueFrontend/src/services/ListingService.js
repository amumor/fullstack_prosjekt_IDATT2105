// src/services/ListingService.js

import {
    ApiClient,
    ListingControllerApi,
    ListingCreationRequestDTO
} from '@/api';

const timeout = 1000 * 60 * 2; // 2-minute timeout
const baseURL = 'http://localhost:8080';

/**
 * Creates a new listing with the provided details.
 *
 * @param {Object} listing - An object containing listing details.
 * @param {string} listing.title - The title of the listing.
 * @param {string} listing.description - The description of the listing.
 * @param {string} listing.categoryName - The category name.
 * @param {string} listing.listingStatus - The status of the listing.
 * @param {number} listing.price - The price of the listing.
 * @param {number} listing.latitude - The latitude coordinate.
 * @param {number} listing.longitude - The longitude coordinate.
 * @returns {Promise<Object>} A promise that resolves to the created ListingResponseDTO.
 * @throws {Error} If listing creation fails.
 *
 * @example
 * createListing({
 *   title: 'Vintage Car',
 *   description: 'A well-maintained vintage car',
 *   categoryName: 'Cars',
 *   listingStatus: 'ACTIVE',
 *   price: 15000,
 *   latitude: 40.7128,
 *   longitude: -74.0060
 * })
 *   .then(response => console.log('Listing created:', response))
 *   .catch(error => console.error('Creation failed:', error));
 */
export function createListing(listing) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const listingApi = new ListingControllerApi(myClient);
    const listingCreationRequestDTO = new ListingCreationRequestDTO();
    listingCreationRequestDTO.title = listing.title;
    listingCreationRequestDTO.description = listing.description;
    listingCreationRequestDTO.categoryName = listing.categoryName;
    listingCreationRequestDTO.listingStatus = listing.listingStatus;
    listingCreationRequestDTO.price = listing.price;
    listingCreationRequestDTO.latitude = listing.latitude;
    listingCreationRequestDTO.longitude = listing.longitude;

    return listingApi.create(listingCreationRequestDTO)
        .then(listingResponseDTO => {
            // Optionally log or process the response
            return listingResponseDTO;
        })
        .catch(error => {
            console.error('Listing creation failed:', error);
            throw error;
        });
}

/**
 * Retrieves a listing by its ID.
 *
 * @param {string} id - The ID of the listing.
 * @returns {Promise<Object>} A promise that resolves to a ListingResponseDTO.
 * @throws {Error} If fetching the listing fails.
 *
 * @example
 * getListingById('listingId123')
 *   .then(listing => console.log('Listing retrieved:', listing))
 *   .catch(error => console.error('Failed to fetch listing:', error));
 */
export function getListingById(id) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const listingApi = new ListingControllerApi(myClient);

    return listingApi.getById(id)
        .then(listingResponseDTO => {
            return listingResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve listing by ID:', error);
            throw error;
        });
}

/**
 * Retrieves listing suggestions with optional pagination.
 *
 * @param {Object} [opts] - Optional parameters.
 * @param {number} [opts.page=0] - The page number (default 0).
 * @param {number} [opts.size=10] - The number of listings per page (default 10).
 * @returns {Promise<Object>} A promise that resolves to a ListingListResponseDTO.
 * @throws {Error} If fetching listing suggestions fails.
 *
 * @example
 * getListingSuggestions({ page: 0, size: 10 })
 *   .then(listings => console.log('Listing suggestions:', listings))
 *   .catch(error => console.error('Failed to retrieve suggestions:', error));
 */
export function getListingSuggestions(opts = { page: 0, size: 10 }) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const listingApi = new ListingControllerApi(myClient);

    return listingApi.getSuggestions(opts)
        .then(listingListResponseDTO => {
            return listingListResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve listing suggestions:', error);
            throw error;
        });
}