package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.ListingRepository;
import edu.ntnu.SpringBackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class BookmarkServiceTest {

  @Autowired private BookmarkService bookmarkService;
  @Autowired private UserRepository userRepository;
  @Autowired private ListingRepository listingRepository;

  private User testUser;
  private Listing testListing;

  @BeforeEach
  void setup() {
    testUser = userRepository.save(User.builder()
            .email("test@example.com")
            .password("Password123")
            .firstName("Test")
            .lastName("User")
            .phoneNumber("123456789")
            .role(Role.ADMIN)
            .build());

    testListing = listingRepository.save(Listing.builder()
            .title("Original Title")
            .description("Original description")
            .price(100.0)
            .category(Category.FURNITURE)
            .status(ListingStatus.ACTIVE)
            .latitude(59.0)
            .longitude(10.0)
            .seller(testUser)
            .build());
  }

  @Test
  void getBookmarksByUser_Success() {
    bookmarkService.addBookmark(testUser, testListing);
    List<Bookmark> bookmarks = bookmarkService.getBookmarksByUser(testUser);

    assertThat(bookmarks).hasSize(1);
    assertThat(bookmarks.getFirst().getListing().getTitle()).isEqualTo("Original Title");
    assertThat(bookmarks.getFirst().getListing().getId()).isEqualTo(testListing.getId());
  }

  @Test
  void getBookmarkByListing_Success() {
    bookmarkService.addBookmark(testUser, testListing);
    List<Bookmark> bookmarks = bookmarkService.getBookmarksByListing(testListing);

    assertThat(bookmarks).hasSize(1);
    assertThat(bookmarks.getFirst().getListing().getId()).isEqualTo(testListing.getId());
  }

  @Test
  void getBookmarksByUserAndListing_Success() {
    bookmarkService.addBookmark(testUser, testListing);
    Bookmark bookmark = bookmarkService.getBookmarksByUserAndListing(testUser, testListing).get();

    assertThat(bookmark.getListing().getId()).isEqualTo(testListing.getId());
    assertThat(bookmark.getUser().getId()).isEqualTo(testUser.getId());
  }

  @Test
  void addBookmark_Success() {
    Bookmark bookmark = bookmarkService.addBookmark(testUser, testListing);

    assertThat(bookmark.getId()).isGreaterThan(0);
    assertThat(bookmark.getUser().getEmail()).isEqualTo("test@example.com");
  }

  @Test
  void deleteBookmark_Success() {
    bookmarkService.addBookmark(testUser, testListing);
    bookmarkService.deleteBookmark(testUser, testListing);

    boolean isBookmarked = bookmarkService.isBookmarked(testUser, testListing);
    assertThat(isBookmarked).isFalse();
  }

  @Test
  void deleteBookmarkById_Success() {
    bookmarkService.addBookmark(testUser, testListing);
    List<Bookmark> bookmarks = bookmarkService.getBookmarksByUser(testUser);
    int bookmarkId = bookmarks.getFirst().getId();

    bookmarkService.deleteBookmarkById(bookmarkId);

    assertThat(bookmarkService.getBookmarksByListing(testListing).isEmpty()).isTrue();
  }

  @Test
  void isBookmarked_Success() {
    bookmarkService.addBookmark(testUser, testListing);
    boolean isBookmarked = bookmarkService.isBookmarked(testUser, testListing);

    assertThat(isBookmarked).isTrue();
  }

}

