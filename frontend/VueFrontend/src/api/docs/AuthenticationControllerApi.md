# OpenApiDocumentationFindNo.AuthenticationControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticate**](AuthenticationControllerApi.md#authenticate) | **POST** /api/v1/auth/authenticate | 
[**register**](AuthenticationControllerApi.md#register) | **POST** /api/v1/auth/register | 
[**registerAdmin**](AuthenticationControllerApi.md#registerAdmin) | **POST** /api/v1/auth/register/admin | 



## authenticate

> TokenResponseDTO authenticate(authenticationRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.AuthenticationControllerApi();
let authenticationRequestDTO = new OpenApiDocumentationFindNo.AuthenticationRequestDTO(); // AuthenticationRequestDTO | 
apiInstance.authenticate(authenticationRequestDTO, (error, data, response) => {
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
 **authenticationRequestDTO** | [**AuthenticationRequestDTO**](AuthenticationRequestDTO.md)|  | 

### Return type

[**TokenResponseDTO**](TokenResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## register

> TokenResponseDTO register(userRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.AuthenticationControllerApi();
let userRequestDTO = new OpenApiDocumentationFindNo.UserRequestDTO(); // UserRequestDTO | 
apiInstance.register(userRequestDTO, (error, data, response) => {
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
 **userRequestDTO** | [**UserRequestDTO**](UserRequestDTO.md)|  | 

### Return type

[**TokenResponseDTO**](TokenResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## registerAdmin

> TokenResponseDTO registerAdmin(userRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.AuthenticationControllerApi();
let userRequestDTO = new OpenApiDocumentationFindNo.UserRequestDTO(); // UserRequestDTO | 
apiInstance.registerAdmin(userRequestDTO, (error, data, response) => {
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
 **userRequestDTO** | [**UserRequestDTO**](UserRequestDTO.md)|  | 

### Return type

[**TokenResponseDTO**](TokenResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

