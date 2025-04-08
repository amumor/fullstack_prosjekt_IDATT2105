# OpenApiDocumentationFindNo.ListingControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](ListingControllerApi.md#create) | **POST** /api/v1/listing/create | Create a new listing
[**deleteListing**](ListingControllerApi.md#deleteListing) | **DELETE** /api/v1/listing/delete/{id} | Delete a listing
[**getByCategory**](ListingControllerApi.md#getByCategory) | **GET** /api/v1/listing/get-by-category | Get listings by category
[**getById**](ListingControllerApi.md#getById) | **GET** /api/v1/listing/id/{id} | Get a listing by ID
[**getBySeller**](ListingControllerApi.md#getBySeller) | **GET** /api/v1/listing/get-by-seller | Get listings by seller
[**getByTitle**](ListingControllerApi.md#getByTitle) | **GET** /api/v1/listing/get-by-title | Get listings by title (search)
[**getSuggestions**](ListingControllerApi.md#getSuggestions) | **GET** /api/v1/listing/get-suggestions | Get suggestions for listings based on search history
[**updateListing**](ListingControllerApi.md#updateListing) | **PUT** /api/v1/listing/update/{id} | Update a listing



## create

> ListingResponseDTO create(listing, opts)

Create a new listing

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let listing = new OpenApiDocumentationFindNo.ListingCreationRequestDTO(); // ListingCreationRequestDTO | 
let opts = {
  'images': ["null"] // [File] | 
};
apiInstance.create(listing, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **listing** | [**ListingCreationRequestDTO**](ListingCreationRequestDTO.md)|  | 
 **images** | **[File]**|  | [optional] 

### Return type

[**ListingResponseDTO**](ListingResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: */*


## deleteListing

> deleteListing(id)

Delete a listing

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let id = "id_example"; // String | 
apiInstance.deleteListing(id).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  | 

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getByCategory

> ListingListResponseDTO getByCategory(categoryName, opts)

Get listings by category

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let categoryName = "categoryName_example"; // String | 
let opts = {
  'page': 0, // Number | 
  'size': 10 // Number | 
};
apiInstance.getByCategory(categoryName, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **categoryName** | **String**|  | 
 **page** | **Number**|  | [optional] [default to 0]
 **size** | **Number**|  | [optional] [default to 10]

### Return type

[**ListingListResponseDTO**](ListingListResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getById

> ListingResponseDTO getById(id)

Get a listing by ID

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let id = "id_example"; // String | 
apiInstance.getById(id).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  | 

### Return type

[**ListingResponseDTO**](ListingResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getBySeller

> ListingListResponseDTO getBySeller(opts)

Get listings by seller

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let opts = {
  'page': 0, // Number | 
  'size': 10 // Number | 
};
apiInstance.getBySeller(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Number**|  | [optional] [default to 0]
 **size** | **Number**|  | [optional] [default to 10]

### Return type

[**ListingListResponseDTO**](ListingListResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getByTitle

> ListingListResponseDTO getByTitle(title, opts)

Get listings by title (search)

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let title = "title_example"; // String | 
let opts = {
  'page': 0, // Number | 
  'size': 10 // Number | 
};
apiInstance.getByTitle(title, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **title** | **String**|  | 
 **page** | **Number**|  | [optional] [default to 0]
 **size** | **Number**|  | [optional] [default to 10]

### Return type

[**ListingListResponseDTO**](ListingListResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getSuggestions

> ListingListResponseDTO getSuggestions(opts)

Get suggestions for listings based on search history

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let opts = {
  'page': 0, // Number | 
  'size': 10 // Number | 
};
apiInstance.getSuggestions(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Number**|  | [optional] [default to 0]
 **size** | **Number**|  | [optional] [default to 10]

### Return type

[**ListingListResponseDTO**](ListingListResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## updateListing

> ListingResponseDTO updateListing(id, opts)

Update a listing

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let id = "id_example"; // String | 
let opts = {
  'updateListingRequest': new OpenApiDocumentationFindNo.UpdateListingRequest() // UpdateListingRequest | 
};
apiInstance.updateListing(id, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  | 
 **updateListingRequest** | [**UpdateListingRequest**](UpdateListingRequest.md)|  | [optional] 

### Return type

[**ListingResponseDTO**](ListingResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

