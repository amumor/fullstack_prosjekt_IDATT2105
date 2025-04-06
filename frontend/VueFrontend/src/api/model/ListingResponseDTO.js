/**
 * OpenAPI Documentation - find.no
 * OpenApi documentation for find.no
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

import ApiClient from '../ApiClient.js';

/**
 * The ListingResponseDTO model module.
 * @module model/ListingResponseDTO
 * @version 1.0
 */
class ListingResponseDTO {
    /**
     * Constructs a new <code>ListingResponseDTO</code>.
     * @alias module:model/ListingResponseDTO
     */
    constructor() { 
        
        ListingResponseDTO.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>ListingResponseDTO</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/ListingResponseDTO} obj Optional instance to populate.
     * @return {module:model/ListingResponseDTO} The populated <code>ListingResponseDTO</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new ListingResponseDTO();

            if (data.hasOwnProperty('id')) {
                obj['id'] = ApiClient.convertToType(data['id'], 'String');
            }
            if (data.hasOwnProperty('sellerFirstName')) {
                obj['sellerFirstName'] = ApiClient.convertToType(data['sellerFirstName'], 'String');
            }
            if (data.hasOwnProperty('sellerLastName')) {
                obj['sellerLastName'] = ApiClient.convertToType(data['sellerLastName'], 'String');
            }
            if (data.hasOwnProperty('title')) {
                obj['title'] = ApiClient.convertToType(data['title'], 'String');
            }
            if (data.hasOwnProperty('description')) {
                obj['description'] = ApiClient.convertToType(data['description'], 'String');
            }
            if (data.hasOwnProperty('categoryName')) {
                obj['categoryName'] = ApiClient.convertToType(data['categoryName'], 'String');
            }
            if (data.hasOwnProperty('listingStatus')) {
                obj['listingStatus'] = ApiClient.convertToType(data['listingStatus'], 'String');
            }
            if (data.hasOwnProperty('price')) {
                obj['price'] = ApiClient.convertToType(data['price'], 'Number');
            }
            if (data.hasOwnProperty('latitude')) {
                obj['latitude'] = ApiClient.convertToType(data['latitude'], 'Number');
            }
            if (data.hasOwnProperty('longitude')) {
                obj['longitude'] = ApiClient.convertToType(data['longitude'], 'Number');
            }
            if (data.hasOwnProperty('createdAt')) {
                obj['createdAt'] = ApiClient.convertToType(data['createdAt'], 'Date');
            }
            if (data.hasOwnProperty('lastEditedAt')) {
                obj['lastEditedAt'] = ApiClient.convertToType(data['lastEditedAt'], 'Date');
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>ListingResponseDTO</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>ListingResponseDTO</code>.
     */
    static validateJSON(data) {
        // ensure the json data is a string
        if (data['id'] && !(typeof data['id'] === 'string' || data['id'] instanceof String)) {
            throw new Error("Expected the field `id` to be a primitive type in the JSON string but got " + data['id']);
        }
        // ensure the json data is a string
        if (data['sellerFirstName'] && !(typeof data['sellerFirstName'] === 'string' || data['sellerFirstName'] instanceof String)) {
            throw new Error("Expected the field `sellerFirstName` to be a primitive type in the JSON string but got " + data['sellerFirstName']);
        }
        // ensure the json data is a string
        if (data['sellerLastName'] && !(typeof data['sellerLastName'] === 'string' || data['sellerLastName'] instanceof String)) {
            throw new Error("Expected the field `sellerLastName` to be a primitive type in the JSON string but got " + data['sellerLastName']);
        }
        // ensure the json data is a string
        if (data['title'] && !(typeof data['title'] === 'string' || data['title'] instanceof String)) {
            throw new Error("Expected the field `title` to be a primitive type in the JSON string but got " + data['title']);
        }
        // ensure the json data is a string
        if (data['description'] && !(typeof data['description'] === 'string' || data['description'] instanceof String)) {
            throw new Error("Expected the field `description` to be a primitive type in the JSON string but got " + data['description']);
        }
        // ensure the json data is a string
        if (data['categoryName'] && !(typeof data['categoryName'] === 'string' || data['categoryName'] instanceof String)) {
            throw new Error("Expected the field `categoryName` to be a primitive type in the JSON string but got " + data['categoryName']);
        }
        // ensure the json data is a string
        if (data['listingStatus'] && !(typeof data['listingStatus'] === 'string' || data['listingStatus'] instanceof String)) {
            throw new Error("Expected the field `listingStatus` to be a primitive type in the JSON string but got " + data['listingStatus']);
        }

        return true;
    }


}



/**
 * @member {String} id
 */
ListingResponseDTO.prototype['id'] = undefined;

/**
 * @member {String} sellerFirstName
 */
ListingResponseDTO.prototype['sellerFirstName'] = undefined;

/**
 * @member {String} sellerLastName
 */
ListingResponseDTO.prototype['sellerLastName'] = undefined;

/**
 * @member {String} title
 */
ListingResponseDTO.prototype['title'] = undefined;

/**
 * @member {String} description
 */
ListingResponseDTO.prototype['description'] = undefined;

/**
 * @member {String} categoryName
 */
ListingResponseDTO.prototype['categoryName'] = undefined;

/**
 * @member {String} listingStatus
 */
ListingResponseDTO.prototype['listingStatus'] = undefined;

/**
 * @member {Number} price
 */
ListingResponseDTO.prototype['price'] = undefined;

/**
 * @member {Number} latitude
 */
ListingResponseDTO.prototype['latitude'] = undefined;

/**
 * @member {Number} longitude
 */
ListingResponseDTO.prototype['longitude'] = undefined;

/**
 * @member {Date} createdAt
 */
ListingResponseDTO.prototype['createdAt'] = undefined;

/**
 * @member {Date} lastEditedAt
 */
ListingResponseDTO.prototype['lastEditedAt'] = undefined;






export default ListingResponseDTO;

