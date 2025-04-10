// src/services/ListingService.js

import {
    ApiClient,
    ListingControllerApi,
    ListingCreationRequestDTO
} from '@/api';
import {serviceConfigParams} from '@/services/ServiceSetup.js';

const {timeout, baseURL} = serviceConfigParams();

import request from 'superagent';

/**
 * Creates a new listing with the provided details.
 *
 * @param {Object} listing - An object containing listing details.
 * @param {string} listing.title - The title of the listing.
 * @param {string} listing.description - The description of the listing.
 * @param {string} listing.categoryName - The category name.
 * @param {string} listing.listingStatus - The status of the listing. Enum: ACTIVE, INACTIVE or SOLD
 * @param {number} listing.price - The price of the listing.
 * @param {number} listing.latitude - The latitude coordinate.
 * @param {number} listing.longitude - The longitude coordinate.
 * @param {Array.<string>} listing.imagesToDelete - Array of images to delete, if empty no images are deleted
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
    // Prepare the listing data to be sent in the request.
    const requestData = {
        title: listing.title,
        description: listing.description,
        categoryName: listing.categoryName,
        listingStatus: listing.listingStatus, // Should be a valid enum value like 'ACTIVE'
        price: listing.price,
        latitude: listing.latitude,
        longitude: listing.longitude,
        imagesToDelete: listing.imagesToDelete || [],
    };
    console.log('imagesToDelete:', requestData.imagesToDelete);

    // Initialize the superagent request.
    let req = request
        .post('http://localhost:8080/api/v1/listing/create')
        .set('Authorization', `Bearer ${token}`)
        .type('multipart/form-data');

    // Add listing details as fields to the form.
    Object.keys(requestData).forEach(key => {
        req.field(key, requestData[key]);
    });

    // Add the images if provided.
    if (images.length > 0) {
        images.forEach(image => {
            req.attach('images', image);
        });
    }

    // Send the request and return a promise.
    console.log('Request:', req);
    return req
        .then(response => {
            return response.body;
        })
        .catch(error => {
            console.error('Listing creation failed:', error);
            throw new Error(`Listing creation failed: ${error.message}`);
        });
}

export async function createListing2(formData, token) {
    try {
        const response = await request
            .post('http://localhost:8080/api/v1/listing/create')
            .set('Authorization', `Bearer ${token}`)
            .type('multipart/form-data')
            .send(formData); // Send FormData
        return response.body;
    } catch (error) {
        console.error('Listing creation failed:', error);
        throw new Error(`Listing creation failed: ${error.message}`);
    }
}

export async function createListingWithoutImage(listing, token) {
    try {
        const response = await fetch('http://localhost:8080/api/v1/listing/create-split', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(listing) // Send the listing as JSON
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json(); // Parse and return the JSON response
    } catch (error) {
        console.error('Error creating listing without image:', error);
        throw new Error(`Listing creation failed: ${error.message}`);
    }
}

/**
 * Retrieves a listing by its ID using superagent.
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
export const getListingById = (id) => {
    return request
        .get(`http://localhost:8080/api/v1/listing/id/${id}`)
        .then(res => res.body)
        .catch(error => {
            console.error('Failed to retrieve listing by ID:', error);
            throw error;
        });
};


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
export function getListingSuggestions(opts = {page: 0, size: 10}) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;

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
    return request
        .put(`http://localhost:8080/api/v1/listing/update/${id}`) // Assuming your API endpoint for updating is like this
        .set('Authorization', `Bearer ${token}`)
        .send(updateData) // Send the updateData as the request body
        .then(res => res.body) // Assuming the response contains the updated listing
        .catch(err => {
            console.error('Failed to update listing:', err);
            throw err;
        });
}

/**
 * Retrieves listings by category name.
 *
 * @param {string} categoryName - The category name to filter listings.
 * @returns {Promise<Object>} A promise that resolves to the listings.
 * @throws {Error} If the request fails.
 */
export async function getListingsByCategory(categoryName) {
    const url = new URL('http://localhost:8080/api/v1/listing/get-by-category');
    url.searchParams.append('categoryName', categoryName);

    try {
        const response = await fetch(url, {
            method: 'GET',
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch listings: ${response.statusText}`);
        }

        const data = await response.json();
        return data.listings;
    } catch (error) {
        console.error('Error fetching listings by category:', error);
        throw error;
    }
}

export async function getListingsBySeller(token, page, size) {
    const url = new URL('http://localhost:8080/api/v1/listing/get-by-seller');
    url.searchParams.append('page', page);
    url.searchParams.append('size', size);

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // Add JWT token
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch listings: ${response.statusText}`);
        }

        const data = await response.json();
        return data.listings;
    } catch (error) {
        console.error('Error fetching listings by category:', error);
        throw error;
    }
}

/**
 * Deletes a listing by its ID.
 *
 * @param {string} token - The authentication token.
 * @param {UUID} id - The ID of the listing to delete.
 * @returns {Promise<void>} - Resolves when the listing is successfully deleted.
 */
export const deleteListing = (token, id) => {
    return request
        .delete(`http://localhost:8080/api/v1/listing/delete/${id}`)
        .set('Authorization', `Bearer ${token}`)
        .then(() => {
            console.log(`Listing with ID ${id} deleted successfully.`);
        })
        .catch(err => {
            console.error(`Failed to delete listing with ID ${id}:`, err);
            throw err;
        });
};

/**
 * Get listings by title (search), with optional pagination.
 * 
 * @param {String} title - The title or keyword to search for.
 * @param {Object} opts - Optional parameters.
 * @param {Number} opts.page - Page number (default 0).
 * @param {Number} opts.size - Page size (default 10).
 * @param {String} token - Optional JWT token for auth (if needed).
 * @returns {Promise<Object>} A promise that resolves to the response body.
 */
export const getListingsByTitle = (title, opts = {}) => {
    const { page = 0, size = 10 } = opts;

    return request
        .get('http://localhost:8080/api/v1/listing/get-by-title')
        .query({ title, page, size })
        .then(res => res.body)
        .catch(err => {
            if (err.status === 404) {
                // No listings found — return empty result to handle gracefully
                return { listings: [] };
            }

            console.error('Failed to retrieve listings by title:', err);
            throw err;
        });
};