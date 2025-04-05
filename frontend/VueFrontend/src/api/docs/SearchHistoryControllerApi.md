# OpenApiDocumentationFindNo.SearchHistoryControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**add**](SearchHistoryControllerApi.md#add) | **POST** /api/v1/search-history/add | 
[**findByUserId**](SearchHistoryControllerApi.md#findByUserId) | **GET** /api/v1/search-history/get-my-history | 



## add

> SearchHistoryResponseDTO add(searchHistoryRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.SearchHistoryControllerApi();
let searchHistoryRequestDTO = new OpenApiDocumentationFindNo.SearchHistoryRequestDTO(); // SearchHistoryRequestDTO | 
apiInstance.add(searchHistoryRequestDTO, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **searchHistoryRequestDTO** | [**SearchHistoryRequestDTO**](SearchHistoryRequestDTO.md)|  | 

### Return type

[**SearchHistoryResponseDTO**](SearchHistoryResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## findByUserId

> SearchHistoryListResponseDTO findByUserId()



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.SearchHistoryControllerApi();
apiInstance.findByUserId((error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**SearchHistoryListResponseDTO**](SearchHistoryListResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

