// src/services/BookmarkService.js

import request from 'superagent';

const BASE_URL = 'http://localhost:8080/api/v1/bookmarks'; // Adjust if needed

/**
 * Creates a new bookmark for the given listing.
 *
 * @param {Object} bookmarkData - Data required for creating a bookmark.
 * @param {string} bookmarkData.listingId - The ID of the listing to bookmark.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the bookmark response.
 */
export const createBookmark = (bookmarkData, token) => {
    return request
        .post(`${BASE_URL}/create`)
        .set('Authorization', `Bearer ${token}`)
        .send(bookmarkData)
        .then(res => res.body)
        .catch(err => {
            console.error('Failed to create bookmark:', err);
            throw err;
        });
};

/**
 * Deletes a bookmark by its ID.
 *
 * @param {string} bookmarkId - The ID of the bookmark to delete.
 * @param {string} token - JWT token.
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 */
export const deleteBookmark = (bookmarkId, token) => {
    return request
        .delete(`${BASE_URL}/${bookmarkId}`)
        .set('Authorization', `Bearer ${token}`)
        .then(() => {
            // No content returned; resolved implicitly
        })
        .catch(err => {
            console.error('Failed to delete bookmark:', err);
            throw err;
        });
};

/**
 * Retrieves all bookmarks for the currently authenticated user.
 *
 * @param {string} token - JWT token.
 * @returns {Promise<Array>} A promise that resolves to a list of bookmarks.
 */
export const getUserBookmarks = (token) => {
    return request
        .get(`${BASE_URL}/my-bookmarks`)
        .set('Authorization', `Bearer ${token}`)
        .then(res => res.body)
        .catch(err => {
            console.error('Failed to retrieve user bookmarks:', err);
            throw err;
        });
};
