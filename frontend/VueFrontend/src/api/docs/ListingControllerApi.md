# OpenApiDocumentationFindNo.ListingControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](ListingControllerApi.md#create) | **POST** /api/v1/listing/create | 
[**getById**](ListingControllerApi.md#getById) | **GET** /api/v1/listing/id/{id} | 
[**getSuggestions**](ListingControllerApi.md#getSuggestions) | **GET** /api/v1/listing/get-suggestions | 



## create

> ListingResponseDTO create(listingCreationRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ListingControllerApi();
let listingCreationRequestDTO = new OpenApiDocumentationFindNo.ListingCreationRequestDTO(); // ListingCreationRequestDTO | 
apiInstance.create(listingCreationRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **listingCreationRequestDTO** | [**ListingCreationRequestDTO**](ListingCreationRequestDTO.md)|  | 

### Return type

[**ListingResponseDTO**](ListingResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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

