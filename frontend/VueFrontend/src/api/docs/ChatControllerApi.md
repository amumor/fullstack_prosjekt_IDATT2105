# OpenApiDocumentationFindNo.ChatControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMessageToChat**](ChatControllerApi.md#addMessageToChat) | **POST** /api/v1/chat/{chatId}/message | 
[**createChatFromBuyer**](ChatControllerApi.md#createChatFromBuyer) | **POST** /api/v1/listing/{listingId}/create | 
[**getAllChatsForListing**](ChatControllerApi.md#getAllChatsForListing) | **GET** /api/v1/listing/{listingId}/chats | 
[**getAllChatsForUser**](ChatControllerApi.md#getAllChatsForUser) | **GET** /api/v1/user/my-chats | 
[**getChat**](ChatControllerApi.md#getChat) | **GET** /api/v1/listing/{listingId}/chat | 



## addMessageToChat

> MessageResponsetDTO addMessageToChat(chatId, messageRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let chatId = "chatId_example"; // String | 
let messageRequestDTO = new OpenApiDocumentationFindNo.MessageRequestDTO(); // MessageRequestDTO | 
apiInstance.addMessageToChat(chatId, messageRequestDTO, (error, data, response) => {
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
 **chatId** | **String**|  | 
 **messageRequestDTO** | [**MessageRequestDTO**](MessageRequestDTO.md)|  | 

### Return type

[**MessageResponsetDTO**](MessageResponsetDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## createChatFromBuyer

> ChatResponseDTO createChatFromBuyer(listingId, messageRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let listingId = "listingId_example"; // String | 
let messageRequestDTO = new OpenApiDocumentationFindNo.MessageRequestDTO(); // MessageRequestDTO | 
apiInstance.createChatFromBuyer(listingId, messageRequestDTO, (error, data, response) => {
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
 **listingId** | **String**|  | 
 **messageRequestDTO** | [**MessageRequestDTO**](MessageRequestDTO.md)|  | 

### Return type

[**ChatResponseDTO**](ChatResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## getAllChatsForListing

> [ChatResponseDTO] getAllChatsForListing(listingId)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let listingId = "listingId_example"; // String | 
apiInstance.getAllChatsForListing(listingId, (error, data, response) => {
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
 **listingId** | **String**|  | 

### Return type

[**[ChatResponseDTO]**](ChatResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getAllChatsForUser

> [ChatResponseDTO] getAllChatsForUser()



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
apiInstance.getAllChatsForUser((error, data, response) => {
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

[**[ChatResponseDTO]**](ChatResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getChat

> ChatResponseDTO getChat(listingId)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.ChatControllerApi();
let listingId = "listingId_example"; // String | 
apiInstance.getChat(listingId, (error, data, response) => {
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
 **listingId** | **String**|  | 

### Return type

[**ChatResponseDTO**](ChatResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

