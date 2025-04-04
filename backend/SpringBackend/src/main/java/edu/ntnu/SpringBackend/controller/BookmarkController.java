package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.BookmarkRequestDTO;
import edu.ntnu.SpringBackend.dto.BookmarkResponseDTO;
import edu.ntnu.SpringBackend.mapper.BookmarkMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Controller for handling bookmark-related requests.
 * This class provides endpoints for creating, retrieving, and deleting bookmarks.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
  private final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
  private final BookmarkService bookmarkService;

  /**
   * Get all bookmarks for a user
   *
   * @param user the authenticated user
   * @return a list of bookmark response DTOs
   */
  @GetMapping("/my-bookmarks")
  @Operation(summary = "Getting all bookmarks for a user", security = @SecurityRequirement(name = "bearerAuth"))
  public List<BookmarkResponseDTO> getUserBookmarks(
         @AuthenticationPrincipal User user
  ) {
    logger.info("Received GET request to get users bookmarks");

    return bookmarkService.getBookmarksByUser(user).stream()
            .map(BookmarkMapper::toDto)
            .collect(Collectors.toList());
  }

  /**
   * Create a new bookmark for a listing.
   *
   * @param user the authenticated user creating the bookmark
   * @param bookmarkRequestDTO the request DTO containing the listing ID
   * @return the created bookmark response DTO
   */
  @PostMapping("/create")
  @Operation(summary = "Create a bookmark for a listing", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BookmarkResponseDTO> createBookmark(
          @AuthenticationPrincipal User user,
          @RequestBody BookmarkRequestDTO bookmarkRequestDTO
  ) {
    logger.info("Received POST request to create bookmark");

    return ResponseEntity.ok(BookmarkMapper.toDto(bookmarkService.createBookmark(bookmarkRequestDTO, user)));
  }

  /**
   * Delete a bookmark by its ID.
   *
   * @param user the authenticated user deleting the bookmark
   * @param bookmarkId the ID of the bookmark to delete
   * @return a response entity with no content
   */
  @DeleteMapping("/{bookmarkId}")
  @Operation(summary = "Delete a bookmark", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Void> deleteBookmark(
          @AuthenticationPrincipal User user,
          @PathVariable UUID bookmarkId
  ) {
    logger.info("Received DELETE request to delete bookmark with ID: {}", bookmarkId);
    bookmarkService.deleteBookmark(bookmarkId, user);

    return ResponseEntity.noContent().build();
  }
}
