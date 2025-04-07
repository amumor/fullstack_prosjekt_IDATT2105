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


import ApiClient from "../ApiClient";
import ListingCreationRequestDTO from '../model/ListingCreationRequestDTO';
import ListingListResponseDTO from '../model/ListingListResponseDTO';
import ListingResponseDTO from '../model/ListingResponseDTO';

/**
* ListingController service.
* @module api/ListingControllerApi
* @version 1.0
*/
export default class ListingControllerApi {

    /**
    * Constructs a new ListingControllerApi. 
    * @alias module:api/ListingControllerApi
    * @class
    * @param {module:ApiClient} [apiClient] Optional API client implementation to use,
    * default to {@link module:ApiClient#instance} if unspecified.
    */
    constructor(apiClient) {
        this.apiClient = apiClient || ApiClient.instance;
    }



    /**
     * @param {module:model/ListingCreationRequestDTO} listingCreationRequestDTO 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingResponseDTO} and HTTP response
     */
    createWithHttpInfo(listingCreationRequestDTO) {
      let postBody = listingCreationRequestDTO;
      // verify the required parameter 'listingCreationRequestDTO' is set
      if (listingCreationRequestDTO === undefined || listingCreationRequestDTO === null) {
        throw new Error("Missing the required parameter 'listingCreationRequestDTO' when calling create");
      }

      let pathParams = {
      };
      let queryParams = {
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = ['application/json'];
      let accepts = ['*/*'];
      let returnType = ListingResponseDTO;
      return this.apiClient.callApi(
        '/api/v1/listing/create', 'POST',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * @param {module:model/ListingCreationRequestDTO} listingCreationRequestDTO 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingResponseDTO}
     */
    create(listingCreationRequestDTO) {
      return this.createWithHttpInfo(listingCreationRequestDTO)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * @param {String} id 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingResponseDTO} and HTTP response
     */
    getByIdWithHttpInfo(id) {
      let postBody = null;
      // verify the required parameter 'id' is set
      if (id === undefined || id === null) {
        throw new Error("Missing the required parameter 'id' when calling getById");
      }

      let pathParams = {
        'id': id
      };
      let queryParams = {
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ListingResponseDTO;
      return this.apiClient.callApi(
        '/api/v1/listing/id/{id}', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * @param {String} id 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingResponseDTO}
     */
    getById(id) {
      return this.getByIdWithHttpInfo(id)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * @param {Object} opts Optional parameters
     * @param {Number} [page = 0)] 
     * @param {Number} [size = 10)] 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingListResponseDTO} and HTTP response
     */
    getSuggestionsWithHttpInfo(opts) {
      opts = opts || {};
      let postBody = null;

      let pathParams = {
      };
      let queryParams = {
        'page': opts['page'],
        'size': opts['size']
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ListingListResponseDTO;
      return this.apiClient.callApi(
        '/api/v1/listing/get-suggestions', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * @param {Object} opts Optional parameters
     * @param {Number} opts.page  (default to 0)
     * @param {Number} opts.size  (default to 10)
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingListResponseDTO}
     */
    getSuggestions(opts) {
      return this.getSuggestionsWithHttpInfo(opts)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


}
