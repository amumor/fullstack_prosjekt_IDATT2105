package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.BookmarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookmarkService {
  private final BookmarkRepository bookmarkRepository;
  Logger logger = LoggerFactory.getLogger(BookmarkRepository.class);

  public BookmarkService(BookmarkRepository bookmarkRepository) {
    this.bookmarkRepository = bookmarkRepository;
  }

  public List<Bookmark> getBookmarksByUser(User user) {
    logger.info("> Getting bookmark by user: {}", user.getId());
    return bookmarkRepository.findByUser(user);
  }

  public List<Bookmark> getBookmarksByListing(Listing listing) {
    logger.info("> Getting bookmark by listing: {}", listing.getId());
    return bookmarkRepository.findByListing(listing);
  }

  public Optional<Bookmark> getBookmarksByUserAndListing(User user, Listing listing) {
    logger.info("> Getting bookmark by user: {} and listing: {}", user.getId(), listing.getId());
    return bookmarkRepository.findByUserAndListing(user, listing);
  }

  @Transactional
  public Bookmark addBookmark(User user, Listing listing) {
    logger.info("> Adding bookmark to listing: {} for user: {}", listing.getId(), user.getId());
    Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndListing(user, listing);
    if (existingBookmark.isPresent()) {
      logger.info("> Existing bookmark found");
      return existingBookmark.get();
    }

    Bookmark bookmark = Bookmark.builder()
            .user(user)
            .listing(listing)
            .build();
    return bookmarkRepository.save(bookmark);
  }

  @Transactional
  public void deleteBookmarkById(UUID id) {
    logger.info("> Deleting bookmark: {}", id);
    bookmarkRepository.deleteById(id);
  }

  @Transactional
  public void deleteBookmark(User user, Listing listing) {
    logger.info("> Deleting bookmark by user: {} and listing: {}", user.getId(), listing.getId());
    bookmarkRepository.findByUserAndListing(user, listing)
            .ifPresent(bookmarkRepository::delete);
  }

  public boolean isBookmarked(User user, Listing listing) {
    logger.info("checking if listing: {} is bookmarked", listing.getId());
    return bookmarkRepository.findByUserAndListing(user, listing).isPresent();
  }
}
