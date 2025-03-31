package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {
  private final BookmarkRepository bookmarkRepository;

  public BookmarkService(BookmarkRepository bookmarkRepository) {
    this.bookmarkRepository = bookmarkRepository;
  }

  public List<Bookmark> getBookmarksByUser(User user) {
    return bookmarkRepository.findByUser(user);
  }

  public List<Bookmark> getBookmarksByListing(Listing listing) {
    return bookmarkRepository.findByListing(listing);
  }

  public Optional<Bookmark> getBookmarksByUserAndListing(User user, Listing listing) {
    return bookmarkRepository.findByUserAndListing(user, listing);
  }

  @Transactional
  public Bookmark addBookmark(User user, Listing listing) {
    Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndListing(user, listing);
    if (existingBookmark.isPresent()) {
      return existingBookmark.get();
    }

    Bookmark bookmark = Bookmark.builder()
            .user(user)
            .listing(listing)
            .build();
    return bookmarkRepository.save(bookmark);
  }

  @Transactional
  public void deleteBookmarkById(int id) {
    bookmarkRepository.deleteById(id);
  }

  @Transactional
  public void deleteBookmark(User user, Listing listing) {
    bookmarkRepository.findByUserAndListing(user, listing)
            .ifPresent(bookmarkRepository::delete);
  }

  public boolean isBookmarked(User user, Listing listing) {
    return bookmarkRepository.findByUserAndListing(user, listing).isPresent();
  }
}
