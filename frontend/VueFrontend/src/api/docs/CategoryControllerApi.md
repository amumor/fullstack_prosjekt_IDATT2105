# OpenApiDocumentationFindNo.CategoryControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**callDelete**](CategoryControllerApi.md#callDelete) | **DELETE** /api/v1/category/delete/{id} | Delete a category
[**create1**](CategoryControllerApi.md#create1) | **POST** /api/v1/category/create | Create a new category
[**getAll**](CategoryControllerApi.md#getAll) | **GET** /api/v1/category/all | Get all categories
[**getById1**](CategoryControllerApi.md#getById1) | **GET** /api/v1/category/id/{id} | Get a category by ID
[**getByName**](CategoryControllerApi.md#getByName) | **GET** /api/v1/category/name/{name} | Get a category by name



## callDelete

> callDelete(id)

Delete a category

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.CategoryControllerApi();
let id = "id_example"; // String | 
apiInstance.callDelete(id).then(() => {
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


## create1

> CategoryResponseDTO create1(categoryCreationRequestDTO)

Create a new category

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.CategoryControllerApi();
let categoryCreationRequestDTO = new OpenApiDocumentationFindNo.CategoryCreationRequestDTO(); // CategoryCreationRequestDTO | 
apiInstance.create1(categoryCreationRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **categoryCreationRequestDTO** | [**CategoryCreationRequestDTO**](CategoryCreationRequestDTO.md)|  | 

### Return type

[**CategoryResponseDTO**](CategoryResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## getAll

> CategoryListResponseDTO getAll()

Get all categories

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.CategoryControllerApi();
apiInstance.getAll().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

This endpoint does not need any parameter.

### Return type

[**CategoryListResponseDTO**](CategoryListResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getById1

> CategoryResponseDTO getById1(id)

Get a category by ID

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.CategoryControllerApi();
let id = "id_example"; // String | 
apiInstance.getById1(id).then((data) => {
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

[**CategoryResponseDTO**](CategoryResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getByName

> CategoryResponseDTO getByName(name)

Get a category by name

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.CategoryControllerApi();
let name = "name_example"; // String | 
apiInstance.getByName(name).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | 

### Return type

[**CategoryResponseDTO**](CategoryResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

