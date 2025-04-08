// src/services/ChatService.js

import {
    ApiClient,
    ChatControllerApi,
    MessageRequestDTO
} from '@/api';
import { serviceConfigParams } from '@/services/ServiceSetup.js';

const { timeout, baseURL } = serviceConfigParams();

/**
 * Adds a message to an existing chat.
 *
 * @param {string} chatId - The ID of the chat.
 * @param {string} messageContent - The content of the message to add.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to a MessageResponseDTO.
 * @throws {Error} If adding the message fails.
 *
 * @example
 * addMessageToChat('chat123', 'Hello, how are you?', 'jwt-token')
 *   .then(response => console.log('Message added:', response))
 *   .catch(error => console.error('Failed to add message:', error));
 */
export function addMessageToChat(chatId, messageContent, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const chatApi = new ChatControllerApi(client);

    const messageRequestDTO = new MessageRequestDTO();
    messageRequestDTO.content = messageContent;

    return chatApi.addMessageToChat(chatId, messageRequestDTO)
        .then(messageResponseDTO => messageResponseDTO)
        .catch(error => {
            console.error('Failed to add message to chat:', error);
            throw error;
        });
}

/**
 * Creates a new chat from the buyer for a given listing with an initial message.
 *
 * @param {string} listingId - The ID of the listing.
 * @param {string} messageContent - The initial message content.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to a ChatResponseDTO.
 * @throws {Error} If chat creation fails.
 *
 * @example
 * createChatFromBuyer('listing123', 'Is this still available?', 'jwt-token')
 *   .then(chat => console.log('Chat created:', chat))
 *   .catch(error => console.error('Failed to create chat:', error));
 */
export function createChatFromBuyer(listingId, messageContent, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const chatApi = new ChatControllerApi(client);

    const messageRequestDTO = new MessageRequestDTO();
    messageRequestDTO.content = messageContent;

    return chatApi.createChatFromBuyer(listingId, messageRequestDTO)
        .then(chatResponseDTO => chatResponseDTO)
        .catch(error => {
            console.error('Failed to create chat from buyer:', error);
            throw error;
        });
}

/**
 * Retrieves all chats for a given listing.
 *
 * @param {string} listingId - The ID of the listing.
 * @param {string} token - JWT token.
 * @returns {Promise<Array>} A promise that resolves to an array of ChatResponseDTO objects.
 * @throws {Error} If fetching chats fails.
 *
 * @example
 * getAllChatsForListing('listing123', 'jwt-token')
 *   .then(chats => console.log('Chats for listing:', chats))
 *   .catch(error => console.error('Failed to fetch chats:', error));
 */
export function getAllChatsForListing(listingId, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const chatApi = new ChatControllerApi(client);

    return chatApi.getAllChatsForListing(listingId)
        .then(chats => chats)
        .catch(error => {
            console.error('Failed to retrieve chats for listing:', error);
            throw error;
        });
}

/**
 * Retrieves all chats for the authenticated user.
 *
 * @param {string} token - JWT token.
 * @returns {Promise<Array>} A promise that resolves to an array of ChatResponseDTO objects.
 * @throws {Error} If fetching the user's chats fails.
 *
 * @example
 * getAllChatsForUser('jwt-token')
 *   .then(chats => console.log('User chats:', chats))
 *   .catch(error => console.error('Failed to fetch user chats:', error));
 */
export function getAllChatsForUser(token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const chatApi = new ChatControllerApi(client);

    return chatApi.getAllChatsForUser()
        .then(chats => chats)
        .catch(error => {
            console.error('Failed to retrieve user chats:', error);
            throw error;
        });
}

/**
 * Retrieves a chat for a given listing.
 *
 * @param {string} listingId - The ID of the listing associated with the chat.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to a ChatResponseDTO.
 * @throws {Error} If fetching the chat fails.
 *
 * @example
 * getChat('listing123', 'jwt-token')
 *   .then(chat => console.log('Chat:', chat))
 *   .catch(error => console.error('Failed to retrieve chat:', error));
 */
export function getChat(listingId, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const chatApi = new ChatControllerApi(client);

    return chatApi.getChat(listingId)
        .then(chatResponseDTO => chatResponseDTO)
        .catch(error => {
            console.error('Failed to retrieve chat:', error);
            throw error;
        });
}