package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.BookmarkResponseDTO;
import edu.ntnu.SpringBackend.model.Bookmark;

/**
 * Mapper class for converting Bookmark entities to BookmarkResponseDTOs.
 * <p>
 * This class is responsible for mapping the fields of a Bookmark entity to a BookmarkResponseDTO.
 * It uses the Builder pattern to create instances of BookmarkResponseDTO.
 * </p>
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
public class BookmarkMapper {

  /**
   * Converts a Bookmark entity to a BookmarkResponseDTO.
   *
   * @param bookmark The Bookmark entity to convert.
   * @return A BookmarkResponseDTO containing the mapped fields from the Bookmark entity.
   */
  public static BookmarkResponseDTO toDto(Bookmark bookmark) {
    if (bookmark == null) return null;

    return BookmarkResponseDTO.builder()
            .id(bookmark.getId())
            .listingId(bookmark.getListing().getId())
            .savedAt(bookmark.getSavedAt())
            .build();
  }
}
