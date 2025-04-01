package edu.ntnu.SpringBackend.service;

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
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
@Rollback
class ListingServiceTest {

  @Autowired private ListingService listingService;
  @Autowired private ListingRepository listingRepository;
  @Autowired private UserRepository userRepository;

  private User seller;

  @BeforeEach
  void setup() {
    seller = userRepository.save(User.builder()
            .email("seller@example.com")
            .password("StrongPassword1!")
            .firstName("Seller")
            .lastName("One")
            .phoneNumber("12345678")
            .role(Role.USER)
            .build());
  }

  private Listing validListing() {
    return Listing.builder()
            .title("Test Title")
            .description("This is a valid description.")
            .price(100.0)
            .category(Category.CLOTHES)
            .latitude(59.9)
            .longitude(10.7)
            .seller(seller)
            .build();
  }


  @Test
  void createListing_Success() {
    Listing listing = listingService.createListing(validListing());

    assertThat(listing.getId()).isGreaterThan(0);
    assertThat(listing.getStatus()).isEqualTo(ListingStatus.ACTIVE);
  }

  @Test
  void createListing_FailsWithSoldStatus() {
    Listing listing = validListing();
    listing.setStatus(ListingStatus.SOLD);

    Listing result = listingService.createListing(listing);
    assertThat(result.getStatus()).isEqualTo(ListingStatus.ACTIVE);
  }

  @Test
  void createListing_FailsWhenMissingTitle() {
    Listing listing = validListing();
    listing.setTitle(null);

    assertThatThrownBy(() -> listingService.createListing(listing))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Title cannot be empty");
  }

  @Test
  void createListing_FailsWhenShortDescription() {
    Listing listing = validListing();
    listing.setDescription("short");

    assertThatThrownBy(() -> listingService.createListing(listing))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Description must be at least");
  }

  @Test
  void createListing_FailsWhenPriceNegative() {
    Listing listing = validListing();
    listing.setPrice(-10.0);

    assertThatThrownBy(() -> listingService.createListing(listing))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Price must be a positive number");
  }

  @Test
  void createListing_FailsWhenCategoryNull() {
    Listing listing = validListing();
    listing.setCategory(null);

    assertThatThrownBy(() -> listingService.createListing(listing))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Category must not be null");
  }

  @Test
  void getListingById_Success() {
    Listing created = listingService.createListing(validListing());
    Listing found = listingService.getListingById(created.getId());

    assertThat(found.getId()).isEqualTo(created.getId());
  }

  @Test
  void getListingById_NotFound() {
    assertThatThrownBy(() -> listingService.getListingById(99999))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void getListingBySeller_Success() {
    listingService.createListing(validListing());
    List<Listing> results = listingService.getListingBySeller(seller);

    assertThat(results).hasSizeGreaterThanOrEqualTo(1);
  }

  @Test
  void getListingBySeller_NoListingsFound() {
    User newSeller = userRepository.save(seller.toBuilder()
            .email("new@seller.com")
            .phoneNumber("87654321")
            .build());

    assertThatThrownBy(() -> listingService.getListingBySeller(newSeller))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("No listings found");
  }

  @Test
  void getAllListings_Success() {
    listingService.createListing(validListing());
    List<Listing> all = listingService.getAllListings();

    assertThat(all).isNotEmpty();
  }

  @Test
  void getAllListings_EmptyThrows() {
    listingRepository.deleteAll();

    assertThatThrownBy(() -> listingService.getAllListings())
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("No listings found");
  }

  @Test
  void updateListing_Success() {
    Listing listing = listingService.createListing(validListing());

    Listing update = Listing.builder()
            .title("Updated Title")
            .description("Updated valid description")
            .price(200.0)
            .category(Category.ELECTRONICS)
            .status(ListingStatus.INACTIVE)
            .build();

    Listing result = listingService.updateListing(listing.getId(), update);

    assertThat(result.getTitle()).isEqualTo("Updated Title");
    assertThat(result.getPrice()).isEqualTo(200.0);
    assertThat(result.getStatus()).isEqualTo(ListingStatus.INACTIVE);
  }

  @Test
  void updateListing_NotFound() {
    Listing update = Listing.builder().title("Updated").build();

    assertThatThrownBy(() -> listingService.updateListing(99999, update))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void updateListing_FailsOnInvalidTitle() {
    Listing listing = listingService.createListing(validListing());

    Listing update = Listing.builder().title("").build();

    assertThatThrownBy(() -> listingService.updateListing(listing.getId(), update))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Title cannot be empty");
  }

  @Test
  void deleteListingById_Success() {
    Listing listing = listingService.createListing(validListing());
    listingService.deleteListingById(listing.getId());

    assertThatThrownBy(() -> listingService.getListingById(listing.getId()))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void deleteListingById_NotFound() {
    assertThatThrownBy(() -> listingService.deleteListingById(99999))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void updateListingStatus_Success() {
    Listing listing = listingService.createListing(validListing());
    Listing updated = listingService.updateListingStatus(listing.getId(), ListingStatus.SOLD);

    assertThat(updated.getStatus()).isEqualTo(ListingStatus.SOLD);
  }

  @Test
  void updateListingStatus_FailsWhenNull() {
    Listing listing = listingService.createListing(validListing());

    assertThatThrownBy(() -> listingService.updateListingStatus(listing.getId(), null))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void updateListingStatus_NotFound() {
    assertThatThrownBy(() -> listingService.updateListingStatus(99999, ListingStatus.SOLD))
            .isInstanceOf(NoSuchElementException.class);
  }
}

