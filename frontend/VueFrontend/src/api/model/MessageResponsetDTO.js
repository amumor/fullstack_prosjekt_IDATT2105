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
 * The MessageResponsetDTO model module.
 * @module model/MessageResponsetDTO
 * @version 1.0
 */
class MessageResponsetDTO {
    /**
     * Constructs a new <code>MessageResponsetDTO</code>.
     * @alias module:model/MessageResponsetDTO
     */
    constructor() { 
        
        MessageResponsetDTO.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>MessageResponsetDTO</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/MessageResponsetDTO} obj Optional instance to populate.
     * @return {module:model/MessageResponsetDTO} The populated <code>MessageResponsetDTO</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new MessageResponsetDTO();

            if (data.hasOwnProperty('chatId')) {
                obj['chatId'] = ApiClient.convertToType(data['chatId'], 'String');
            }
            if (data.hasOwnProperty('senderFirstName')) {
                obj['senderFirstName'] = ApiClient.convertToType(data['senderFirstName'], 'String');
            }
            if (data.hasOwnProperty('senderLastName')) {
                obj['senderLastName'] = ApiClient.convertToType(data['senderLastName'], 'String');
            }
            if (data.hasOwnProperty('content')) {
                obj['content'] = ApiClient.convertToType(data['content'], 'String');
            }
            if (data.hasOwnProperty('sentAt')) {
                obj['sentAt'] = ApiClient.convertToType(data['sentAt'], 'Date');
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>MessageResponsetDTO</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>MessageResponsetDTO</code>.
     */
    static validateJSON(data) {
        // ensure the json data is a string
        if (data['chatId'] && !(typeof data['chatId'] === 'string' || data['chatId'] instanceof String)) {
            throw new Error("Expected the field `chatId` to be a primitive type in the JSON string but got " + data['chatId']);
        }
        // ensure the json data is a string
        if (data['senderFirstName'] && !(typeof data['senderFirstName'] === 'string' || data['senderFirstName'] instanceof String)) {
            throw new Error("Expected the field `senderFirstName` to be a primitive type in the JSON string but got " + data['senderFirstName']);
        }
        // ensure the json data is a string
        if (data['senderLastName'] && !(typeof data['senderLastName'] === 'string' || data['senderLastName'] instanceof String)) {
            throw new Error("Expected the field `senderLastName` to be a primitive type in the JSON string but got " + data['senderLastName']);
        }
        // ensure the json data is a string
        if (data['content'] && !(typeof data['content'] === 'string' || data['content'] instanceof String)) {
            throw new Error("Expected the field `content` to be a primitive type in the JSON string but got " + data['content']);
        }

        return true;
    }


}



/**
 * @member {String} chatId
 */
MessageResponsetDTO.prototype['chatId'] = undefined;

/**
 * @member {String} senderFirstName
 */
MessageResponsetDTO.prototype['senderFirstName'] = undefined;

/**
 * @member {String} senderLastName
 */
MessageResponsetDTO.prototype['senderLastName'] = undefined;

/**
 * @member {String} content
 */
MessageResponsetDTO.prototype['content'] = undefined;

/**
 * @member {Date} sentAt
 */
MessageResponsetDTO.prototype['sentAt'] = undefined;






export default MessageResponsetDTO;

