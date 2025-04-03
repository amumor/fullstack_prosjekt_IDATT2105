package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.BookmarkRequestDTO;
import edu.ntnu.SpringBackend.dto.BookmarkResponseDTO;
import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;

import java.time.LocalDateTime;

public class BookmarkMapper {

  public static Bookmark toEntity(BookmarkRequestDTO dto) {
    return Bookmark.builder()
        .listing(Listing.builder().id(dto.getListingId()).build())
        .savedAt(LocalDateTime.now())
        .build();
  }

  public static BookmarkResponseDTO toDto(Bookmark bookmark) {
    if (bookmark == null) return null;

    return BookmarkResponseDTO.builder()
        .id(bookmark.getId())
        .listingId(bookmark.getListing().getId())
            .userEmail(bookmark.getUser().getEmail())
        .savedAt(bookmark.getSavedAt())
        .build();
  }
}
