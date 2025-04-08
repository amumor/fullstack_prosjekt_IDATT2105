// src/services/BookmarkService.js

import {
    ApiClient,
    BookmarkControllerApi,
    BookmarkRequestDTO
} from '@/api';
import { serviceConfigParams } from '@/services/ServiceSetup.js';

const { timeout, baseURL } = serviceConfigParams();

/**
 * Creates a new bookmark for the given listing.
 *
 * @param {Object} bookmarkData - Data required for creating a bookmark.
 * @param {string} bookmarkData.listingId - The ID of the listing to bookmark.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to a BookmarkResponseDTO object.
 * @throws {Error} If bookmark creation fails.
 *
 * @example
 * createBookmark({ listingId: 'abc123' }, 'jwt.tok.en')
 *   .then(response => console.log('Bookmark created:', response))
 *   .catch(error => console.error('Error creating bookmark:', error));
 */
export function createBookmark(bookmarkData, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bookmarkApi = new BookmarkControllerApi(client);
    const bookmarkRequestDTO = new BookmarkRequestDTO();
    bookmarkRequestDTO.listingId = bookmarkData.listingId;

    return bookmarkApi.createBookmark(bookmarkRequestDTO)
        .then(bookmarkResponseDTO => bookmarkResponseDTO)
        .catch(error => {
            console.error('Failed to create bookmark:', error);
            throw error;
        });
}

/**
 * Deletes a bookmark by its ID.
 *
 * @param {string} bookmarkId - The ID of the bookmark to delete.
 * @param {string} token - JWT token.
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 * @throws {Error} If bookmark deletion fails.
 *
 * @example
 * deleteBookmark('bookmark123', 'jwt.tok.en')
 *   .then(() => console.log('Bookmark deleted successfully'))
 *   .catch(error => console.error('Failed to delete bookmark:', error));
 */
export function deleteBookmark(bookmarkId, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bookmarkApi = new BookmarkControllerApi(client);

    return bookmarkApi.deleteBookmark(bookmarkId)
        .then(() => {
            // If your backend returns 204 (No Content), you might want to resolve with a value:
            return; // or: return { message: 'Bookmark deleted successfully' };
        })
        .catch(error => {
            console.error('Failed to delete bookmark:', error);
            throw error;
        });
}

/**
 * Retrieves all bookmarks for the currently authenticated user.
 *
 * @param {string} token - JWT token.
 * @returns {Promise<Array>} A promise that resolves to an array of BookmarkResponseDTO objects.
 * @throws {Error} If fetching bookmarks fails.
 *
 * @example
 * getUserBookmarks('jwt.tok.en')
 *   .then(bookmarks => console.log('Retrieved bookmarks:', bookmarks))
 *   .catch(error => console.error('Error fetching bookmarks:', error));
 */
export function getUserBookmarks(token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bookmarkApi = new BookmarkControllerApi(client);

    return bookmarkApi.getUserBookmarks()
        .then(bookmarkList => bookmarkList)
        .catch(error => {
            console.error('Failed to retrieve user bookmarks:', error);
            throw error;
        });
}