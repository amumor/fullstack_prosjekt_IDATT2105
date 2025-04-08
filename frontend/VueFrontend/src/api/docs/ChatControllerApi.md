# OpenApiDocumentationFindNo.ChatControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMessageToChat**](ChatControllerApi.md#addMessageToChat) | **POST** /api/v1/chat/{chatId}/message | Add a message to a chat
[**createChatFromBuyer**](ChatControllerApi.md#createChatFromBuyer) | **POST** /api/v1/listing/{listingId}/create | Create a chat between a buyer and a listing
[**getAllChatsForListing**](ChatControllerApi.md#getAllChatsForListing) | **GET** /api/v1/listing/{listingId}/chats | Get all chats for a listing for a seller
[**getAllChatsForUser**](ChatControllerApi.md#getAllChatsForUser) | **GET** /api/v1/user/my-chats | Get all chats for a user
[**getChat**](ChatControllerApi.md#getChat) | **GET** /api/v1/listing/{listingId}/chat | Get a chat between a buyer and a listing



## addMessageToChat

> MessageResponseDTO addMessageToChat(chatId, messageRequestDTO)

Add a message to a chat

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let chatId = "chatId_example"; // String | 
let messageRequestDTO = new OpenApiDocumentationFindNo.MessageRequestDTO(); // MessageRequestDTO | 
apiInstance.addMessageToChat(chatId, messageRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatId** | **String**|  | 
 **messageRequestDTO** | [**MessageRequestDTO**](MessageRequestDTO.md)|  | 

### Return type

[**MessageResponseDTO**](MessageResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## createChatFromBuyer

> ChatResponseDTO createChatFromBuyer(listingId, messageRequestDTO)

Create a chat between a buyer and a listing

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let listingId = "listingId_example"; // String | 
let messageRequestDTO = new OpenApiDocumentationFindNo.MessageRequestDTO(); // MessageRequestDTO | 
apiInstance.createChatFromBuyer(listingId, messageRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **listingId** | **String**|  | 
 **messageRequestDTO** | [**MessageRequestDTO**](MessageRequestDTO.md)|  | 

### Return type

[**ChatResponseDTO**](ChatResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## getAllChatsForListing

> [ChatResponseDTO] getAllChatsForListing(listingId)

Get all chats for a listing for a seller

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let listingId = "listingId_example"; // String | 
apiInstance.getAllChatsForListing(listingId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **listingId** | **String**|  | 

### Return type

[**[ChatResponseDTO]**](ChatResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getAllChatsForUser

> [ChatResponseDTO] getAllChatsForUser()

Get all chats for a user

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
apiInstance.getAllChatsForUser().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

This endpoint does not need any parameter.

### Return type

[**[ChatResponseDTO]**](ChatResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getChat

> ChatResponseDTO getChat(listingId)

Get a chat between a buyer and a listing

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let listingId = "listingId_example"; // String | 
apiInstance.getChat(listingId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **listingId** | **String**|  | 

### Return type

[**ChatResponseDTO**](ChatResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

