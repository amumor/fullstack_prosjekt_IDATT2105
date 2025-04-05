package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.BookmarkResponseDTO;
import edu.ntnu.SpringBackend.model.Bookmark;

public class BookmarkMapper {

  public static BookmarkResponseDTO toDto(Bookmark bookmark) {
    if (bookmark == null) return null;

    return BookmarkResponseDTO.builder()
        .id(bookmark.getId())
        .listingId(bookmark.getListing().getId())
        .savedAt(bookmark.getSavedAt())
        .build();
  }
}
