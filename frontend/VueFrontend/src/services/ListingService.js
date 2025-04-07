// src/services/ListingService.js

import {
    ApiClient,
    ListingControllerApi,
    ListingCreationRequestDTO
} from '@/api';
import { serviceConfigParams } from '@/services/ServiceSetup.js';

const { timeout, baseURL } = serviceConfigParams();

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
 * @param {Array.<File>} [images] - Optional array of image files to upload.
 * @param {string} token - JWT token.
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
 * }, images, 'jwt-token')
 *   .then(response => console.log('Listing created:', response))
 *   .catch(error => console.error('Creation failed:', error));
 */
export function createListing(listing, images = [], token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token,
    };

    const listingApi = new ListingControllerApi(client);
    const listingCreationRequestDTO = new ListingCreationRequestDTO();
    listingCreationRequestDTO.title = listing.title;
    listingCreationRequestDTO.description = listing.description;
    listingCreationRequestDTO.categoryName = listing.categoryName;
    listingCreationRequestDTO.listingStatus = listing.listingStatus;
    listingCreationRequestDTO.price = listing.price;
    listingCreationRequestDTO.latitude = listing.latitude;
    listingCreationRequestDTO.longitude = listing.longitude;
    // Optionally, if needed, you could set imagesToDelete here

    const opts = { images };

    return listingApi.create(listingCreationRequestDTO, opts)
        .then(listingResponseDTO => listingResponseDTO)
        .catch(error => {
            console.error('Listing creation failed:', error);
            throw error;
        });
}

/**
 * Retrieves a listing by its ID.
 *
 * @param {string} id - The ID of the listing.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to a ListingResponseDTO.
 * @throws {Error} If fetching the listing fails.
 *
 * @example
 * getListingById('listingId123', 'jwt-token')
 *   .then(listing => console.log('Listing retrieved:', listing))
 *   .catch(error => console.error('Failed to fetch listing:', error));
 */
export function getListingById(id, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    if (token) {
        client.authentications.bearerAuth = {
            type: 'bearer',
            accessToken: token,
        };
    }
    const listingApi = new ListingControllerApi(client);

    return listingApi.getById(id)
        .then(listingResponseDTO => listingResponseDTO)
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
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to a ListingListResponseDTO.
 * @throws {Error} If fetching listing suggestions fails.
 *
 * @example
 * getListingSuggestions({ page: 0, size: 10 }, 'jwt-token')
 *   .then(listings => console.log('Listing suggestions:', listings))
 *   .catch(error => console.error('Failed to retrieve suggestions:', error));
 */
export function getListingSuggestions(opts = { page: 0, size: 10 }, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token,
    };

    const listingApi = new ListingControllerApi(client);

    return listingApi.getSuggestions(opts)
        .then(listingListResponseDTO => listingListResponseDTO)
        .catch(error => {
            console.error('Failed to retrieve listing suggestions:', error);
            throw error;
        });
}

/**
 * Updates an existing listing with the provided details.
 *
 * @param {string} id - The ID of the listing to update.
 * @param {Object} updateData - An object containing the updated listing details.
 *        The object may include fields such as:
 *        - title: {string} (optional)
 *        - description: {string} (optional)
 *        - categoryName: {string} (optional)
 *        - listingStatus: {string} (optional)
 *        - price: {number} (optional)
 *        - latitude: {number} (optional)
 *        - longitude: {number} (optional)
 *        - images: {Array.<File>} (optional new images)
 *        - imagesToDelete: {Array.<String>} (optional identifiers for images to delete)
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the updated ListingResponseDTO.
 * @throws {Error} If updating the listing fails.
 *
 * @example
 * updateListing('listing123', {
 *   title: 'Updated Title',
 *   description: 'Updated Description',
 *   imagesToDelete: ['oldImage1.jpg']
 * }, 'jwt-token')
 *   .then(response => console.log('Listing updated:', response))
 *   .catch(error => console.error('Update failed:', error));
 */
export function updateListing(id, updateData, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token,
    };

    const listingApi = new ListingControllerApi(client);

    // We assume updateData already matches the expected structure
    // for updateListingRequest as defined in the generated API.
    const opts = {
        updateListingRequest: updateData
    };

    return listingApi.updateListing(id, opts)
        .then(listingResponseDTO => listingResponseDTO)
        .catch(error => {
            console.error('Failed to update listing:', error);
            throw error;
        });
}