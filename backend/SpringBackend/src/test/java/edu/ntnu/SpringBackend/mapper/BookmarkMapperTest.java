package edu.ntnu.SpringBackend.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.ntnu.SpringBackend.dto.BookmarkResponseDTO;
import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;

public class BookmarkMapperTest {

  @Test
  public void testToDto_NullBookmark() {
    BookmarkResponseDTO dto = BookmarkMapper.toDto(null);
    assertNull(dto, "Mapping a null bookmark should return null");
  }

  @Test
  public void testToDto() {
    UUID bookmarkId = UUID.randomUUID();
    LocalDateTime savedAt = LocalDateTime.now();
    UUID listingId = UUID.randomUUID();

    // Create a mocked Listing that returns listingId for getId().
    Listing mockListing = Mockito.mock(Listing.class);
    Mockito.when(mockListing.getId()).thenReturn(listingId);

    Bookmark bookmark = Bookmark.builder()
            .id(bookmarkId)
            .listing(mockListing)
            .savedAt(savedAt)
            .build();

    BookmarkResponseDTO dto = BookmarkMapper.toDto(bookmark);
    assertNotNull(dto);
    assertEquals(bookmarkId, dto.getId());
    assertEquals(listingId, dto.getListingId());
    assertEquals(savedAt, dto.getSavedAt());
  }
}
