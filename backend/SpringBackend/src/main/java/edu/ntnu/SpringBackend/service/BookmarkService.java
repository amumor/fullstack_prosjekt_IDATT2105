package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.BookmarkRequestDTO;
import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.BookmarkRepository;
import edu.ntnu.SpringBackend.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkService {
  private final Logger logger = org.slf4j.LoggerFactory.getLogger(BookmarkService.class);
  private final BookmarkRepository bookmarkRepository;
  private final ListingRepository listingRepository;

  /**
   * Fetches all bookmarks for the current user.
   *
   * @param user The user whose bookmarks are to be fetched.
   * @return List of bookmarks for the current user.
   */
  public List<Bookmark> getBookmarksByUser(User user) {
    logger.info("> Getting bookmarks for user: {}", user.getEmail());
    return bookmarkRepository.findByUser(user);
  }

  /**
   * Creates a new bookmark for the given listing and user.
   *
   * @param bookmarkRequestDTO The request DTO containing the listing ID.
   * @param user              The user who is creating the bookmark.
   * @return The created bookmark.
   */
  @Transactional
  public Bookmark createBookmark(BookmarkRequestDTO bookmarkRequestDTO, User user) {
    logger.info("> Handling bookmark creation for user: {}", user.getEmail());

    Listing listing = listingRepository.findById(bookmarkRequestDTO.getListingId())
            .orElseThrow(() -> new ObjectNotFoundException(bookmarkRequestDTO.getListingId(), "Listing not found"));

    Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndListing(user, listing);
    if (existingBookmark.isPresent()) {
      logger.info("> Bookmark already exists for this listing");
      return existingBookmark.get();
    }

    Bookmark bookmark = Bookmark.builder()
            .listing(listing)
            .user(user)
            .savedAt(LocalDateTime.now())
            .build();

    logger.info("> Bookmark created for listing: {}", listing.getTitle());
    return bookmarkRepository.save(bookmark);
  }

  /**
   * Deletes a bookmark by its UUID.
   *
   * @param bookmarkId The ID of the bookmark to be deleted.
   */
  @Transactional
  public void deleteBookmark(UUID bookmarkId, String email) {
    logger.info("> Deleting bookmark with ID: {}", bookmarkId);
    Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
            .orElseThrow(() -> new ObjectNotFoundException(bookmarkId, "Bookmark not found"));

    if (!bookmark.getUser().getEmail().equals(email)) {
      logger.warn("> User is not authorized to delete bookmark");
      throw new AccessDeniedException("User not authorized to delete this bookmark.");
    }

    bookmarkRepository.delete(bookmark);
    logger.info("> Bookmark deleted successfully");
  }
}
