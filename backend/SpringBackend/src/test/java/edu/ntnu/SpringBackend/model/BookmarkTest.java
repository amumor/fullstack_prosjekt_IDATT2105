package edu.ntnu.SpringBackend.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BookmarkTest {

  @Test
  public void testOnCreate() {
    Bookmark bookmark = new Bookmark();
    bookmark.onCreate();
    assertNotNull(bookmark.getSavedAt(), "savedAt should be set during onCreate");
  }

  @Test
  public void testBuilder() {
    // Use Mockito to create dummy objects for dependencies.
    User dummyUser = Mockito.mock(User.class);
    Listing dummyListing = Mockito.mock(Listing.class);
    UUID bookmarkId = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();

    Bookmark bookmark = Bookmark.builder()
            .id(bookmarkId)
            .user(dummyUser)
            .listing(dummyListing)
            .savedAt(now)
            .build();

    assertEquals(bookmarkId, bookmark.getId());
    assertEquals(dummyUser, bookmark.getUser());
    assertEquals(dummyListing, bookmark.getListing());
    assertEquals(now, bookmark.getSavedAt());
  }
}
