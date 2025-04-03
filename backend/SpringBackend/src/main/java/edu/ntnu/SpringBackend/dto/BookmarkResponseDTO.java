package edu.ntnu.SpringBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Bookmark responses.
 */
@Data
@Builder
public class BookmarkResponseDTO {
  private UUID id;
  private UUID listingId;
  private String userEmail;
  private LocalDateTime savedAt;
}
