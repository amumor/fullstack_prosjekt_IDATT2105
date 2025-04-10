package edu.ntnu.SpringBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for Bookmark responses.
 * <p>
 * This class is used to transfer bookmark data between the client and server.
 * It contains the ID, listing ID, and timestamp of the bookmark.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
public class BookmarkResponseDTO {

  /**
   * The unique identifier of the bookmark.
   * This field is automatically generated and cannot be null.
   */
  private UUID id;

  /**
   * The unique identifier of the listing associated with the bookmark.
   * This field cannot be null.
   */
  private UUID listingId;

  /**
   * The timestamp of when the bookmark was created.
   * This field cannot be null.
   */
  private LocalDateTime savedAt;
}
