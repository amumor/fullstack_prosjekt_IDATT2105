# OpenApiDocumentationFindNo.ListingControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](ListingControllerApi.md#create) | **POST** /api/v1/listing/create | 
[**deleteListing**](ListingControllerApi.md#deleteListing) | **DELETE** /api/v1/listing/delete/{id} | 
[**getById**](ListingControllerApi.md#getById) | **GET** /api/v1/listing/id/{id} | 
[**getByTitle**](ListingControllerApi.md#getByTitle) | **GET** /api/v1/listing/get-by-title | 
[**getSuggestions**](ListingControllerApi.md#getSuggestions) | **GET** /api/v1/listing/get-suggestions | 
[**updateListing**](ListingControllerApi.md#updateListing) | **PUT** /api/v1/listing/update/{id} | 



## create

> ListingResponseDTO create(listing, opts)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: */*


## deleteListing

> deleteListing(id)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getById

> ListingResponseDTO getById(id)



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


## getByTitle

> ListingListResponseDTO getByTitle(title, opts)



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



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## updateListing

> ListingResponseDTO updateListing(id, opts)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

