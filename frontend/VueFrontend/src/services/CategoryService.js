// src/services/CategoryService.js

import {
    ApiClient,
    CategoryControllerApi,
    CategoryCreationRequestDTO
} from '@/api';
import { serviceConfigParams } from '@/services/ServiceSetup.js';

const { timeout, baseURL } = serviceConfigParams();

/**
 * Creates a new category with the given details.
 *
 * @param {string} categoryName - The name of the category.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the created CategoryResponseDTO.
 * @throws {Error} If category creation fails.
 *
 * @example
 * createCategory({ name: 'Electronics' }, 'jwt-token')
 *   .then(response => console.log('Category created successfully:', response))
 *   .catch(error => console.error('Category creation failed:', error));
 */
// export function createCategory(category, token) { // TODO: fix later or remove
//     const client = new ApiClient(baseURL);
//     client.timeout = timeout;
//     client.authentications.bearerAuth = { type: 'bearer', accessToken: token };
//
//     const categoryApi = new CategoryControllerApi(client);
//     const categoryCreationRequestDTO = new CategoryCreationRequestDTO();
//     categoryCreationRequestDTO.name = category.name;
//
//     return categoryApi.create1(categoryCreationRequestDTO)
//         .then(categoryResponseDTO => categoryResponseDTO)
//         .catch(error => {
//             console.error('Category creation failed:', error);
//             throw error;
//         });
// }
export const createCategory = (categoryName, token) => {
    return request
        .post('http://localhost:8080/api/v1/category/create')
        .set('Authorization', `Bearer ${token}`)
        .send({ name: categoryName })
        .then(res => res.body)
        .catch(err => {
            console.error('Failed to create category:', err);
            throw err;
        });
};

/**
 * Deletes a category by its ID.
 *
 * @param {string} id - The ID of the category to delete.
 * @param {string} token - JWT token.
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 * @throws {Error} If deletion fails.
 *
 * @example
 * deleteCategory('categoryId123', 'jwt-token')
 *   .then(() => console.log('Category deleted successfully.'))
 *   .catch(error => console.error('Failed to delete category:', error));
 */
export function deleteCategory(id, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = { type: 'bearer', accessToken: token };

    const categoryApi = new CategoryControllerApi(client);
    return categoryApi.callDelete(id)
        .then(() => {
            // Deletion succeeded; no data returned.
        })
        .catch(error => {
            console.error('Failed to delete category:', error);
            throw error;
        });
}

/**
 * Retrieves all categories.
 *
 * @param {string} token - JWT Token
 * @returns {Promise<Object>} A promise that resolves to a CategoryListResponseDTO object.
 * @throws {Error} If fetching categories fails.
 *
 * @example
 * getAllCategories()
 *   .then(response => console.log('Categories retrieved successfully:', response))
 *   .catch(error => console.error('Failed to retrieve categories:', error));
 */
// export function getAllCategories(token) { // TODO: Fix at a later stage
//     const client = new ApiClient(baseURL);
//     client.timeout = timeout;
//     client.authentications.bearerAuth = { type: 'bearer', accessToken: token };
//
//     const categoryApi = new CategoryControllerApi(client);
//     return categoryApi.getAll()
//         .then(categoryListResponseDTO => categoryListResponseDTO)
//         .catch(error => {
//             console.error('Failed to retrieve categories:', error);
//             throw error;
//         });
// }
// CategoryService.js
import request from 'superagent';

export const getAllCategories = (token) => {
    return request
        .get('http://localhost:8080/api/v1/category/all')
        .set('Authorization', `Bearer ${token}`)
        .then(res => res.body)
        .catch(err => {
            console.error('Failed to retrieve categories:', err);
            throw err;
        });
};

/**
 * Retrieves a category by its ID.
 *
 * @param {string} id - The ID of the category.
 * @param {string} token - JWT token
 * @returns {Promise<Object>} A promise that resolves to a CategoryResponseDTO object.
 * @throws {Error} If fetching the category fails.
 *
 * @example
 * getCategoryById('categoryId123')
 *   .then(category => console.log('Category retrieved successfully:', category))
 *   .catch(error => console.error('Failed to retrieve category by ID:', error));
 */
export function getCategoryById(id, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = { type: 'bearer', accessToken: token };

    const categoryApi = new CategoryControllerApi(client);
    return categoryApi.getById1(id)
        .then(categoryResponseDTO => categoryResponseDTO)
        .catch(error => {
            console.error('Failed to retrieve category by ID:', error);
            throw error;
        });
}

/**
 * Retrieves a category by its name.
 *
 * @param {string} name - The name of the category.
 * @param {string} token - JWT token
 * @returns {Promise<Object>} A promise that resolves to a CategoryResponseDTO object.
 * @throws {Error} If fetching the category fails.
 *
 * @example
 * getCategoryByName('Electronics')
 *   .then(category => console.log('Category retrieved successfully:', category))
 *   .catch(error => console.error('Failed to retrieve category by name:', error));
 */
export function getCategoryByName(name, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = { type: 'bearer', accessToken: token };

    const categoryApi = new CategoryControllerApi(client);
    return categoryApi.getByName(name)
        .then(categoryResponseDTO => categoryResponseDTO)
        .catch(error => {
            console.error('Failed to retrieve category by name:', error);
            throw error;
        });
}