# OpenApiDocumentationFindNo.UserControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UserControllerApi.md#deleteUser) | **DELETE** /api/v1/users/{id} | 
[**getUserByEmail**](UserControllerApi.md#getUserByEmail) | **GET** /api/v1/users/email/{email} | 
[**getUserById**](UserControllerApi.md#getUserById) | **GET** /api/v1/users/id/{id} | 
[**updateUser**](UserControllerApi.md#updateUser) | **PUT** /api/v1/users/update-my-profile | 



## deleteUser

> deleteUser(id)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.UserControllerApi();
let id = "id_example"; // String | 
apiInstance.deleteUser(id).then(() => {
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


## getUserByEmail

> UserResponseDTO getUserByEmail(email)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.UserControllerApi();
let email = "email_example"; // String | 
apiInstance.getUserByEmail(email).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **String**|  | 

### Return type

[**UserResponseDTO**](UserResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getUserById

> UserResponseDTO getUserById(id)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.UserControllerApi();
let id = "id_example"; // String | 
apiInstance.getUserById(id).then((data) => {
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

[**UserResponseDTO**](UserResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## updateUser

> UserResponseDTO updateUser(userRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.UserControllerApi();
let userRequestDTO = new OpenApiDocumentationFindNo.UserRequestDTO(); // UserRequestDTO | 
apiInstance.updateUser(userRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userRequestDTO** | [**UserRequestDTO**](UserRequestDTO.md)|  | 

### Return type

[**UserResponseDTO**](UserResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

