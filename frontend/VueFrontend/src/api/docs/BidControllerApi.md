# OpenApiDocumentationFindNo.BidControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**acceptBid**](BidControllerApi.md#acceptBid) | **POST** /api/v1/bids/{bidId}/accept | Accept a bid
[**cancelBid**](BidControllerApi.md#cancelBid) | **POST** /api/v1/bids/{bidId}/cancel | Cancel a bid
[**getAcceptedBids**](BidControllerApi.md#getAcceptedBids) | **GET** /api/v1/bids/accepted | Get all accepted bids for a user
[**placeBid**](BidControllerApi.md#placeBid) | **POST** /api/v1/chat/{chatId}/bids | Place a bid on a chat
[**rejectBid**](BidControllerApi.md#rejectBid) | **POST** /api/v1/bids/{bidId}/reject | Reject a bid



## acceptBid

> BidResponseDTO acceptBid(bidId)

Accept a bid

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.BidControllerApi();
let bidId = "bidId_example"; // String | 
apiInstance.acceptBid(bidId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bidId** | **String**|  | 

### Return type

[**BidResponseDTO**](BidResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## cancelBid

> BidResponseDTO cancelBid(bidId)

Cancel a bid

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.BidControllerApi();
let bidId = "bidId_example"; // String | 
apiInstance.cancelBid(bidId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bidId** | **String**|  | 

### Return type

[**BidResponseDTO**](BidResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getAcceptedBids

> [BidResponseDTO] getAcceptedBids()

Get all accepted bids for a user

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.BidControllerApi();
apiInstance.getAcceptedBids().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

This endpoint does not need any parameter.

### Return type

[**[BidResponseDTO]**](BidResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## placeBid

> BidResponseDTO placeBid(chatId, bidRequestDTO)

Place a bid on a chat

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.BidControllerApi();
let chatId = "chatId_example"; // String | 
let bidRequestDTO = new OpenApiDocumentationFindNo.BidRequestDTO(); // BidRequestDTO | 
apiInstance.placeBid(chatId, bidRequestDTO).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatId** | **String**|  | 
 **bidRequestDTO** | [**BidRequestDTO**](BidRequestDTO.md)|  | 

### Return type

[**BidResponseDTO**](BidResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## rejectBid

> BidResponseDTO rejectBid(bidId)

Reject a bid

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';
let defaultClient = OpenApiDocumentationFindNo.ApiClient.instance;
// Configure Bearer (JWT) access token for authorization: bearerAuth
let bearerAuth = defaultClient.authentications['bearerAuth'];
bearerAuth.accessToken = "YOUR ACCESS TOKEN"

let apiInstance = new OpenApiDocumentationFindNo.BidControllerApi();
let bidId = "bidId_example"; // String | 
apiInstance.rejectBid(bidId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bidId** | **String**|  | 

### Return type

[**BidResponseDTO**](BidResponseDTO.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

