package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for Bookmark requests.
 * <p>
 * This class is used to transfer bookmark data between the client and server.
 * It contains the listing ID of the bookmark, which must be provided.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequestDTO {

  /**
   * The unique identifier of the listing to be bookmarked.
   * This field is required and cannot be null.
   */
  private UUID listingId;
  private UUID userId;
}
