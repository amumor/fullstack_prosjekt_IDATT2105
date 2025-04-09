package edu.ntnu.SpringBackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.access.AccessDeniedException;

import edu.ntnu.SpringBackend.dto.BookmarkRequestDTO;
import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.BookmarkRepository;
import edu.ntnu.SpringBackend.repository.ListingRepository;

public class BookmarkServiceTest {

  @Mock
  private BookmarkRepository bookmarkRepository;

  @Mock
  private ListingRepository listingRepository;

  @InjectMocks
  private BookmarkService bookmarkService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetBookmarksByUser() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");

    List<Bookmark> bookmarks = new ArrayList<>();
    bookmarks.add(Bookmark.builder().build());
    when(bookmarkRepository.findByUser(user)).thenReturn(bookmarks);

    List<Bookmark> result = bookmarkService.getBookmarksByUser(user);
    assertEquals(1, result.size());
    verify(bookmarkRepository, times(1)).findByUser(user);
  }

  @Test
  public void testCreateBookmark_ListingNotFound() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");

    UUID listingId = UUID.randomUUID();
    BookmarkRequestDTO requestDTO = BookmarkRequestDTO.builder().listingId(listingId).build();

    when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class, () -> {
      bookmarkService.createBookmark(requestDTO, user);
    });
    verify(listingRepository, times(1)).findById(listingId);
  }

  @Test
  public void testCreateBookmark_AlreadyExists() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");

    UUID listingId = UUID.randomUUID();
    BookmarkRequestDTO requestDTO = BookmarkRequestDTO.builder().listingId(listingId).build();

    Listing listing = mock(Listing.class);
    when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

    Bookmark existingBookmark = Bookmark.builder().build();
    when(bookmarkRepository.findByUserAndListing(user, listing)).thenReturn(Optional.of(existingBookmark));

    Bookmark result = bookmarkService.createBookmark(requestDTO, user);
    assertEquals(existingBookmark, result);
    verify(bookmarkRepository, never()).save(any(Bookmark.class));
  }

  @Test
  public void testCreateBookmark_New() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");

    UUID listingId = UUID.randomUUID();
    BookmarkRequestDTO requestDTO = BookmarkRequestDTO.builder().listingId(listingId).build();

    Listing listing = mock(Listing.class);
    when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));
    when(bookmarkRepository.findByUserAndListing(user, listing)).thenReturn(Optional.empty());

    Bookmark newBookmark = Bookmark.builder().build();
    when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(newBookmark);

    Bookmark result = bookmarkService.createBookmark(requestDTO, user);
    assertEquals(newBookmark, result);
    verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
  }

  @Test
  public void testDeleteBookmark_NotFound() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");

    UUID bookmarkId = UUID.randomUUID();
    when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class, () -> {
      bookmarkService.deleteBookmark(bookmarkId, user);
    });
    verify(bookmarkRepository, never()).delete(any(Bookmark.class));
  }

  @Test
  public void testDeleteBookmark_Unauthorized() {
    User requester = mock(User.class);
    when(requester.getEmail()).thenReturn("requester@example.com");

    UUID bookmarkId = UUID.randomUUID();

    Bookmark bookmark = Bookmark.builder().build();
    User owner = mock(User.class);
    when(owner.getEmail()).thenReturn("owner@example.com");
    bookmark.setUser(owner);

    when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(bookmark));

    assertThrows(AccessDeniedException.class, () -> {
      bookmarkService.deleteBookmark(bookmarkId, requester);
    });
    verify(bookmarkRepository, never()).delete(any(Bookmark.class));
  }

  @Test
  public void testDeleteBookmark_Success() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");

    UUID bookmarkId = UUID.randomUUID();

    Bookmark bookmark = Bookmark.builder().build();
    // Set the bookmark's user to the same user so deletion is allowed.
    bookmark.setUser(user);

    when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(bookmark));

    // Expect no exception and deletion to be performed.
    assertDoesNotThrow(() -> bookmarkService.deleteBookmark(bookmarkId, user));
    verify(bookmarkRepository, times(1)).delete(bookmark);
  }
}
