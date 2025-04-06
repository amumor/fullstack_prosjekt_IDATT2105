// src/services/BookmarkService.js

import {
  ApiClient,
  BookmarkControllerApi,
  BookmarkRequestDTO
} from '@/api';

const timeout = 1000 * 60 * 2; // 2 min timeout
const baseURL = 'http://localhost:8080';

/**
 * Creates a new bookmark for the given listing.
 *
 * @param {Object} bookmarkData - Data required for creating a bookmark.
 * @param {string} bookmarkData.listingId - The ID of the listing to bookmark.
 * @returns {Promise<Object>} A promise that resolves to a BookmarkResponseDTO object.
 * @throws {Error} If bookmark creation fails.
 *
 * @example
 * createBookmark({ listingId: 'abc123' })
 *   .then(response => {
 *     console.log('Bookmark created:', response);
 *   })
 *   .catch(error => {
 *     console.error('Error creating bookmark:', error);
 *   });
 */
export function createBookmark(bookmarkData) {
  const myClient = new ApiClient(baseURL);
  myClient.timeout = timeout;

  const bookmarkApi = new BookmarkControllerApi(myClient);

  const bookmarkRequestDTO = new BookmarkRequestDTO();
  bookmarkRequestDTO.listingId = bookmarkData.listingId;

  return bookmarkApi.createBookmark(bookmarkRequestDTO)
      .then(bookmarkResponseDTO => {
        // console.log('BookmarkResponseDTO:', bookmarkResponseDTO);
        return bookmarkResponseDTO;
      })
      .catch(error => {
        console.error('Failed to create bookmark:', error);
        throw error;
      });
}

/**
 * Deletes a bookmark by its ID.
 *
 * @param {string} bookmarkId - The ID of the bookmark to delete.
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 * @throws {Error} If bookmark deletion fails.
 *
 * @example
 * deleteBookmark('bookmark123')
 *   .then(() => {
 *     console.log('Bookmark deleted successfully');
 *   })
 *   .catch(error => {
 *     console.error('Failed to delete bookmark:', error);
 *   });
 */
export function deleteBookmark(bookmarkId) {
  const myClient = new ApiClient(baseURL);
  myClient.timeout = timeout;

  const bookmarkApi = new BookmarkControllerApi(myClient);

  return bookmarkApi.deleteBookmark(bookmarkId)
      .then(() => {
        // TODO: add return on 204 no content.
        console.log('Bookmark deleted successfully');
      })
      .catch(error => {
        console.error('Failed to delete bookmark:', error);
        throw error;
      });
}

/**
 * Retrieves all bookmarks for the currently authenticated user.
 *
 * @returns {Promise<Array>} A promise that resolves to an array of BookmarkResponseDTO objects.
 * @throws {Error} If fetching bookmarks fails.
 *
 * @example
 * getUserBookmarks()
 *   .then(bookmarks => {
 *     console.log('Retrieved bookmarks:', bookmarks);
 *   })
 *   .catch(error => {
 *     console.error('Error fetching bookmarks:', error);
 *   });
 */
export function getUserBookmarks() {
  const myClient = new ApiClient(baseURL);
  myClient.timeout = timeout;

  const bookmarkApi = new BookmarkControllerApi(myClient);

  return bookmarkApi.getUserBookmarks()
      .then(bookmarkList => {
        // console.log('Bookmark list:', bookmarkList);
        return bookmarkList;
      })
      .catch(error => {
        console.error('Failed to retrieve user bookmarks:', error);
        throw error;
      });
}