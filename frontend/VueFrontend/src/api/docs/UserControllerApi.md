# OpenApiDocumentationFindNo.UserControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UserControllerApi.md#deleteUser) | **DELETE** /api/v1/users/{id} | Delete user by id
[**getUserByEmail**](UserControllerApi.md#getUserByEmail) | **GET** /api/v1/users/email/{email} | Getting user info by email
[**getUserById**](UserControllerApi.md#getUserById) | **GET** /api/v1/users/id/{id} | Getting user info by user id
[**updateUser**](UserControllerApi.md#updateUser) | **PUT** /api/v1/users/update-my-profile | Update user info



## deleteUser

> deleteUser(id)

Delete user by id

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

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

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getUserByEmail

> UserResponseDTO getUserByEmail(email)

Getting user info by email

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

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

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getUserById

> UserResponseDTO getUserById(id)

Getting user info by user id

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

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

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## updateUser

> UserResponseDTO updateUser(userRequestDTO)

Update user info

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

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

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

