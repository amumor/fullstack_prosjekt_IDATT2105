# OpenApiDocumentationFindNo.BidControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**acceptBid**](BidControllerApi.md#acceptBid) | **POST** /api/v1/bids/{bidId}/accept | 
[**cancelBid**](BidControllerApi.md#cancelBid) | **POST** /api/v1/bids/{bidId}/cancel | 
[**getAcceptedBids**](BidControllerApi.md#getAcceptedBids) | **GET** /api/v1/bids/accepted | 
[**placeBid**](BidControllerApi.md#placeBid) | **POST** /api/v1/chat/{chatId}/bids | 
[**rejectBid**](BidControllerApi.md#rejectBid) | **POST** /api/v1/bids/{bidId}/reject | 



## acceptBid

> BidResponseDTO acceptBid(bidId)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## cancelBid

> BidResponseDTO cancelBid(bidId)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getAcceptedBids

> [BidResponseDTO] getAcceptedBids()



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## placeBid

> BidResponseDTO placeBid(chatId, bidRequestDTO)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## rejectBid

> BidResponseDTO rejectBid(bidId)



### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

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

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

