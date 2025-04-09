package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object for Bookmark requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequestDTO {
  private UUID listingId;
  private UUID userId;
}
