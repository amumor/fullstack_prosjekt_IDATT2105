# OpenApiDocumentationFindNo.BookmarkControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createBookmark**](BookmarkControllerApi.md#createBookmark) | **POST** /api/v1/bookmarks/create | Create a bookmark for a listing
[**deleteBookmark**](BookmarkControllerApi.md#deleteBookmark) | **DELETE** /api/v1/bookmarks/{bookmarkId} | Delete a bookmark
[**getUserBookmarks**](BookmarkControllerApi.md#getUserBookmarks) | **GET** /api/v1/bookmarks/my-bookmarks | Getting all bookmarks for a user



## createBookmark

> BookmarkResponseDTO createBookmark(bookmarkRequestDTO)

Create a bookmark for a listing

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.BookmarkControllerApi();
let bookmarkRequestDTO = new OpenApiDocumentationFindNo.BookmarkRequestDTO(); // BookmarkRequestDTO | 
apiInstance.createBookmark(bookmarkRequestDTO, (error, data, response) => {
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
 **bookmarkRequestDTO** | [**BookmarkRequestDTO**](BookmarkRequestDTO.md)|  | 

### Return type

[**BookmarkResponseDTO**](BookmarkResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## deleteBookmark

> deleteBookmark(bookmarkId)

Delete a bookmark

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.BookmarkControllerApi();
let bookmarkId = "bookmarkId_example"; // String | 
apiInstance.deleteBookmark(bookmarkId, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully.');
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bookmarkId** | **String**|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getUserBookmarks

> [BookmarkResponseDTO] getUserBookmarks()

Getting all bookmarks for a user

### Example

```javascript
import OpenApiDocumentationFindNo from 'open_api_documentation_find_no';

let apiInstance = new OpenApiDocumentationFindNo.BookmarkControllerApi();
apiInstance.getUserBookmarks((error, data, response) => {
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

[**[BookmarkResponseDTO]**](BookmarkResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

