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
import UpdateListingRequest from '../model/UpdateListingRequest';

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
     * Create a new listing
     * @param {module:model/ListingCreationRequestDTO} listing 
     * @param {Object} opts Optional parameters
     * @param {Array.<File>} [images] 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingResponseDTO} and HTTP response
     */
    createWithHttpInfo(listing, opts) {
      opts = opts || {};
      let postBody = null;
      // verify the required parameter 'listing' is set
      if (listing === undefined || listing === null) {
        throw new Error("Missing the required parameter 'listing' when calling create");
      }

      let pathParams = {
      };
      let queryParams = {
      };
      let headerParams = {
      };
      let formParams = {
        'listing': listing,
        'images': this.apiClient.buildCollectionParam(opts['images'], 'passthrough')
      };

      let authNames = ['bearerAuth'];
      let contentTypes = ['multipart/form-data'];
      let accepts = ['*/*'];
      let returnType = ListingResponseDTO;
      return this.apiClient.callApi(
        '/api/v1/listing/create', 'POST',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * Create a new listing
     * @param {module:model/ListingCreationRequestDTO} listing 
     * @param {Object} opts Optional parameters
     * @param {Array.<File>} opts.images 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingResponseDTO}
     */
    create(listing, opts) {
      return this.createWithHttpInfo(listing, opts)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * Delete a listing
     * @param {String} id 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing HTTP response
     */
    deleteListingWithHttpInfo(id) {
      let postBody = null;
      // verify the required parameter 'id' is set
      if (id === undefined || id === null) {
        throw new Error("Missing the required parameter 'id' when calling deleteListing");
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

      let authNames = ['bearerAuth'];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = null;
      return this.apiClient.callApi(
        '/api/v1/listing/delete/{id}', 'DELETE',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * Delete a listing
     * @param {String} id 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}
     */
    deleteListing(id) {
      return this.deleteListingWithHttpInfo(id)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * Get listings by category
     * @param {String} categoryName 
     * @param {Object} opts Optional parameters
     * @param {Number} [page = 0)] 
     * @param {Number} [size = 10)] 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingListResponseDTO} and HTTP response
     */
    getByCategoryWithHttpInfo(categoryName, opts) {
      opts = opts || {};
      let postBody = null;
      // verify the required parameter 'categoryName' is set
      if (categoryName === undefined || categoryName === null) {
        throw new Error("Missing the required parameter 'categoryName' when calling getByCategory");
      }

      let pathParams = {
      };
      let queryParams = {
        'categoryName': categoryName,
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
        '/api/v1/listing/get-by-category', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * Get listings by category
     * @param {String} categoryName 
     * @param {Object} opts Optional parameters
     * @param {Number} opts.page  (default to 0)
     * @param {Number} opts.size  (default to 10)
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingListResponseDTO}
     */
    getByCategory(categoryName, opts) {
      return this.getByCategoryWithHttpInfo(categoryName, opts)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * Get a listing by ID
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
     * Get a listing by ID
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
     * Get listings by seller
     * @param {Object} opts Optional parameters
     * @param {Number} [page = 0)] 
     * @param {Number} [size = 10)] 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingListResponseDTO} and HTTP response
     */
    getBySellerWithHttpInfo(opts) {
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

      let authNames = ['bearerAuth'];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ListingListResponseDTO;
      return this.apiClient.callApi(
        '/api/v1/listing/get-by-seller', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * Get listings by seller
     * @param {Object} opts Optional parameters
     * @param {Number} opts.page  (default to 0)
     * @param {Number} opts.size  (default to 10)
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingListResponseDTO}
     */
    getBySeller(opts) {
      return this.getBySellerWithHttpInfo(opts)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * Get listings by title (search)
     * @param {String} title 
     * @param {Object} opts Optional parameters
     * @param {Number} [page = 0)] 
     * @param {Number} [size = 10)] 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingListResponseDTO} and HTTP response
     */
    getByTitleWithHttpInfo(title, opts) {
      opts = opts || {};
      let postBody = null;
      // verify the required parameter 'title' is set
      if (title === undefined || title === null) {
        throw new Error("Missing the required parameter 'title' when calling getByTitle");
      }

      let pathParams = {
      };
      let queryParams = {
        'title': title,
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
        '/api/v1/listing/get-by-title', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * Get listings by title (search)
     * @param {String} title 
     * @param {Object} opts Optional parameters
     * @param {Number} opts.page  (default to 0)
     * @param {Number} opts.size  (default to 10)
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingListResponseDTO}
     */
    getByTitle(title, opts) {
      return this.getByTitleWithHttpInfo(title, opts)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


    /**
     * Get suggestions for listings based on search history
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

      let authNames = ['bearerAuth'];
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
     * Get suggestions for listings based on search history
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


    /**
     * Update a listing
     * @param {String} id 
     * @param {Object} opts Optional parameters
     * @param {module:model/UpdateListingRequest} [updateListingRequest] 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with an object containing data of type {@link module:model/ListingResponseDTO} and HTTP response
     */
    updateListingWithHttpInfo(id, opts) {
      opts = opts || {};
      let postBody = opts['updateListingRequest'];
      // verify the required parameter 'id' is set
      if (id === undefined || id === null) {
        throw new Error("Missing the required parameter 'id' when calling updateListing");
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

      let authNames = ['bearerAuth'];
      let contentTypes = ['application/json'];
      let accepts = ['*/*'];
      let returnType = ListingResponseDTO;
      return this.apiClient.callApi(
        '/api/v1/listing/update/{id}', 'PUT',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, null
      );
    }

    /**
     * Update a listing
     * @param {String} id 
     * @param {Object} opts Optional parameters
     * @param {module:model/UpdateListingRequest} opts.updateListingRequest 
     * @return {Promise} a {@link https://www.promisejs.org/|Promise}, with data of type {@link module:model/ListingResponseDTO}
     */
    updateListing(id, opts) {
      return this.updateListingWithHttpInfo(id, opts)
        .then(function(response_and_data) {
          return response_and_data.data;
        });
    }


}
