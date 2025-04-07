// src/services/CategoryService.js

import {
    ApiClient,
    CategoryControllerApi,
    CategoryCreationRequestDTO
} from '@/api';
import {serviceConfigParams} from "@/services/ServiceSetup.js";

const { bearerTokenAuth, timeout, baseURL } = serviceConfigParams();

/**
 * Creates a new category with the given details.
 *
 * @param {Object} category - An object containing category details.
 * @param {string} category.name - The name of the category.
 * @returns {Promise<Object>} A promise that resolves to the created CategoryResponseDTO.
 * @throws {Error} If category creation fails.
 *
 * @example
 * createCategory({ name: 'Electronics' })
 *   .then(response => {
 *     console.log('Category created successfully:', response);
 *   })
 *   .catch(error => {
 *     console.error('Category creation failed:', error);
 *   });
 */
export function createCategory(category) {
    const Client = new ApiClient(baseURL);
    Client.timeout = timeout;
    Client.authenticationRequestDTO = bearerTokenAuth;

    const categoryApi = new CategoryControllerApi(Client);
    const categoryCreationRequestDTO = new CategoryCreationRequestDTO();
    categoryCreationRequestDTO.name = category.name;

    return categoryApi.create1(categoryCreationRequestDTO)
        .then(categoryResponseDTO => {
            // console.log('CategoryResponseDTO:', categoryResponseDTO);
            return categoryResponseDTO;
        })
        .catch(error => {
            console.error('Category creation failed:', error);
            throw error;
        });
}

/**
 * Deletes a category by its ID.
 *
 * @param {string} id - The ID of the category to delete.
 * @returns {Promise<void>} A promise that resolves if deletion is successful.
 * @throws {Error} If deletion fails.
 *
 * @example
 * deleteCategory('categoryId123')
 *   .then(() => {
 *     console.log('Category deleted successfully.');
 *   })
 *   .catch(error => {
 *     console.error('Failed to delete category:', error);
 *   });
 */
export function deleteCategory(id) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const categoryApi = new CategoryControllerApi(myClient);

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
 * @returns {Promise<Object>} A promise that resolves to a CategoryListResponseDTO object.
 * @throws {Error} If fetching categories fails.
 *
 * @example
 * getAllCategories()
 *   .then(response => {
 *     console.log('Categories retrieved successfully:', response);
 *   })
 *   .catch(error => {
 *     console.error('Failed to retrieve categories:', error);
 *   });
 */
export function getAllCategories() {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const categoryApi = new CategoryControllerApi(myClient);

    return categoryApi.getAll()
        .then(categoryListResponseDTO => {
            // console.log('CategoryListResponseDTO:', categoryListResponseDTO);
            return categoryListResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve categories:', error);
            throw error;
        });
}

/**
 * Retrieves a category by its ID.
 *
 * @param {string} id - The ID of the category.
 * @returns {Promise<Object>} A promise that resolves to a CategoryResponseDTO object.
 * @throws {Error} If fetching the category fails.
 *
 * @example
 * getCategoryById('categoryId123')
 *   .then(category => {
 *     console.log('Category retrieved successfully:', category);
 *   })
 *   .catch(error => {
 *     console.error('Failed to retrieve category by ID:', error);
 *   });
 */
export function getCategoryById(id) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const categoryApi = new CategoryControllerApi(myClient);

    return categoryApi.getById1(id)
        .then(categoryResponseDTO => {
            // console.log('CategoryResponseDTO:', categoryResponseDTO);
            return categoryResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve category by ID:', error);
            throw error;
        });
}

/**
 * Retrieves a category by its name.
 *
 * @param {string} name - The name of the category.
 * @returns {Promise<Object>} A promise that resolves to a CategoryResponseDTO object.
 * @throws {Error} If fetching the category fails.
 *
 * @example
 * getCategoryByName('Electronics')
 *   .then(category => {
 *     console.log('Category retrieved successfully:', category);
 *   })
 *   .catch(error => {
 *     console.error('Failed to retrieve category by name:', error);
 *   });
 */
export function getCategoryByName(name) {
    const myClient = new ApiClient(baseURL);
    myClient.timeout = timeout;

    const categoryApi = new CategoryControllerApi(myClient);

    return categoryApi.getByName(name)
        .then(categoryResponseDTO => {
            // console.log('CategoryResponseDTO:', categoryResponseDTO);
            return categoryResponseDTO;
        })
        .catch(error => {
            console.error('Failed to retrieve category by name:', error);
            throw error;
        });
}