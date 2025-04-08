# OpenApiDocumentationFindNo.SearchHistoryControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**add**](SearchHistoryControllerApi.md#add) | **POST** /api/v1/search-history/add | Add a new search history entry for the authenticated user
[**findByUserId**](SearchHistoryControllerApi.md#findByUserId) | **GET** /api/v1/search-history/get-my-history | Get search history for the authenticated user



## add

> SearchHistoryResponseDTO add(searchHistoryRequestDTO)

Add a new search history entry for the authenticated user

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.SearchHistoryControllerApi();
let searchHistoryRequestDTO = new OpenApiDocumentationFindNo.SearchHistoryRequestDTO(); // SearchHistoryRequestDTO | 
apiInstance.add(searchHistoryRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **searchHistoryRequestDTO** | [**SearchHistoryRequestDTO**](SearchHistoryRequestDTO.md)|  | 

### Return type

[**SearchHistoryResponseDTO**](SearchHistoryResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## findByUserId

> SearchHistoryListResponseDTO findByUserId()

Get search history for the authenticated user

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.SearchHistoryControllerApi();
apiInstance.findByUserId().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

This endpoint does not need any parameter.

### Return type

[**SearchHistoryListResponseDTO**](SearchHistoryListResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

