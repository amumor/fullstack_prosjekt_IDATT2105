package edu.ntnu.SpringBackend.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class BookmarkDTOTest {

  @Test
  public void testBookmarkRequestDTOBuilder() {
    UUID listingId = UUID.randomUUID();
    BookmarkRequestDTO requestDTO = BookmarkRequestDTO.builder()
            .listingId(listingId)
            .build();
    assertEquals(listingId, requestDTO.getListingId());
  }

  @Test
  public void testBookmarkResponseDTOBuilder() {
    UUID id = UUID.randomUUID();
    UUID listingId = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();
    BookmarkResponseDTO responseDTO = BookmarkResponseDTO.builder()
            .id(id)
            .listingId(listingId)
            .savedAt(now)
            .build();
    assertEquals(id, responseDTO.getId());
    assertEquals(listingId, responseDTO.getListingId());
    assertEquals(now, responseDTO.getSavedAt());
  }
}
