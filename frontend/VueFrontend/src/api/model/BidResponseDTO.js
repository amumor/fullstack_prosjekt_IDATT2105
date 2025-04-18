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

import ApiClient from '../ApiClient';

/**
 * The BidResponseDTO model module.
 * @module model/BidResponseDTO
 * @version 1.0
 */
class BidResponseDTO {
    /**
     * Constructs a new <code>BidResponseDTO</code>.
     * @alias module:model/BidResponseDTO
     */
    constructor() { 
        
        BidResponseDTO.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>BidResponseDTO</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/BidResponseDTO} obj Optional instance to populate.
     * @return {module:model/BidResponseDTO} The populated <code>BidResponseDTO</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new BidResponseDTO();

            if (data.hasOwnProperty('id')) {
                obj['id'] = ApiClient.convertToType(data['id'], 'String');
            }
            if (data.hasOwnProperty('chatId')) {
                obj['chatId'] = ApiClient.convertToType(data['chatId'], 'String');
            }
            if (data.hasOwnProperty('price')) {
                obj['price'] = ApiClient.convertToType(data['price'], 'Number');
            }
            if (data.hasOwnProperty('status')) {
                obj['status'] = ApiClient.convertToType(data['status'], 'String');
            }
            if (data.hasOwnProperty('timestamp')) {
                obj['timestamp'] = ApiClient.convertToType(data['timestamp'], 'Date');
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>BidResponseDTO</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>BidResponseDTO</code>.
     */
    static validateJSON(data) {
        // ensure the json data is a string
        if (data['id'] && !(typeof data['id'] === 'string' || data['id'] instanceof String)) {
            throw new Error("Expected the field `id` to be a primitive type in the JSON string but got " + data['id']);
        }
        // ensure the json data is a string
        if (data['chatId'] && !(typeof data['chatId'] === 'string' || data['chatId'] instanceof String)) {
            throw new Error("Expected the field `chatId` to be a primitive type in the JSON string but got " + data['chatId']);
        }
        // ensure the json data is a string
        if (data['status'] && !(typeof data['status'] === 'string' || data['status'] instanceof String)) {
            throw new Error("Expected the field `status` to be a primitive type in the JSON string but got " + data['status']);
        }

        return true;
    }


}



/**
 * @member {String} id
 */
BidResponseDTO.prototype['id'] = undefined;

/**
 * @member {String} chatId
 */
BidResponseDTO.prototype['chatId'] = undefined;

/**
 * @member {Number} price
 */
BidResponseDTO.prototype['price'] = undefined;

/**
 * @member {module:model/BidResponseDTO.StatusEnum} status
 */
BidResponseDTO.prototype['status'] = undefined;

/**
 * @member {Date} timestamp
 */
BidResponseDTO.prototype['timestamp'] = undefined;





/**
 * Allowed values for the <code>status</code> property.
 * @enum {String}
 * @readonly
 */
BidResponseDTO['StatusEnum'] = {

    /**
     * value: "PENDING"
     * @const
     */
    "PENDING": "PENDING",

    /**
     * value: "ACCEPTED"
     * @const
     */
    "ACCEPTED": "ACCEPTED",

    /**
     * value: "REJECTED"
     * @const
     */
    "REJECTED": "REJECTED",

    /**
     * value: "CANCELED"
     * @const
     */
    "CANCELED": "CANCELED"
};



export default BidResponseDTO;

