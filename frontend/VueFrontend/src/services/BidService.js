// src/services/BidService.js

import {
    ApiClient,
    BidControllerApi,
    BidRequestDTO
} from '@/api';
import { serviceConfigParams } from '@/services/ServiceSetup.js';

const { timeout, baseURL } = serviceConfigParams();

/**
 * Accepts a bid by its ID.
 *
 * @param {string} bidId - The ID of the bid to accept.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the BidResponseDTO.
 * @throws {Error} If accepting the bid fails.
 *
 * @example
 * acceptBid('bid123', 'jwt-token')
 *   .then(response => console.log('Bid accepted:', response))
 *   .catch(error => console.error('Accept bid failed:', error));
 */
export function acceptBid(bidId, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bidApi = new BidControllerApi(client);
    return bidApi.acceptBid(bidId)
        .then(bidResponseDTO => bidResponseDTO)
        .catch(error => {
            console.error('Failed to accept bid:', error);
            throw error;
        });
}

/**
 * Cancels a bid by its ID.
 *
 * @param {string} bidId - The ID of the bid to cancel.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the BidResponseDTO.
 * @throws {Error} If cancelling the bid fails.
 *
 * @example
 * cancelBid('bid123', 'jwt-token')
 *   .then(response => console.log('Bid cancelled:', response))
 *   .catch(error => console.error('Cancel bid failed:', error));
 */
export function cancelBid(bidId, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bidApi = new BidControllerApi(client);
    return bidApi.cancelBid(bidId)
        .then(bidResponseDTO => bidResponseDTO)
        .catch(error => {
            console.error('Failed to cancel bid:', error);
            throw error;
        });
}

/**
 * Retrieves all accepted bids.
 *
 * @param {string} token - JWT token.
 * @returns {Promise<Array>} A promise that resolves to an array of BidResponseDTO objects.
 * @throws {Error} If fetching accepted bids fails.
 *
 * @example
 * getAcceptedBids('jwt-token')
 *   .then(bids => console.log('Accepted bids:', bids))
 *   .catch(error => console.error('Failed to get accepted bids:', error));
 */
export function getAcceptedBids(token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bidApi = new BidControllerApi(client);
    return bidApi.getAcceptedBids()
        .then(bidList => bidList)
        .catch(error => {
            console.error('Failed to retrieve accepted bids:', error);
            throw error;
        });
}

/**
 * Places a bid on a chat.
 *
 * @param {string} chatId - The ID of the chat to bid on.
 * @param {number} price - The bid price.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the BidResponseDTO.
 * @throws {Error} If placing the bid fails.
 *
 * @example
 * placeBid('chat123', 250, 'jwt-token')
 *   .then(response => console.log('Bid placed:', response))
 *   .catch(error => console.error('Failed to place bid:', error));
 */
export function placeBid(chatId, price, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bidApi = new BidControllerApi(client);
    const bidRequestDTO = new BidRequestDTO(price);
    return bidApi.placeBid(chatId, bidRequestDTO)
        .then(bidResponseDTO => bidResponseDTO)
        .catch(error => {
            console.error('Failed to place bid:', error);
            throw error;
        });
}

/**
 * Rejects a bid by its ID.
 *
 * @param {string} bidId - The ID of the bid to reject.
 * @param {string} token - JWT token.
 * @returns {Promise<Object>} A promise that resolves to the BidResponseDTO.
 * @throws {Error} If rejecting the bid fails.
 *
 * @example
 * rejectBid('bid123', 'jwt-token')
 *   .then(response => console.log('Bid rejected:', response))
 *   .catch(error => console.error('Failed to reject bid:', error));
 */
export function rejectBid(bidId, token) {
    const client = new ApiClient(baseURL);
    client.timeout = timeout;
    client.authentications.bearerAuth = {
        type: 'bearer',
        accessToken: token
    };

    const bidApi = new BidControllerApi(client);
    return bidApi.rejectBid(bidId)
        .then(bidResponseDTO => bidResponseDTO)
        .catch(error => {
            console.error('Failed to reject bid:', error);
            throw error;
        });
}