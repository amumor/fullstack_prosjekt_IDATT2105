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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class ListingServiceTest {

  @Autowired private ListingService listingService;
  @Autowired private ListingRepository listingRepository;
  @Autowired private UserRepository userRepository;

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
  void getListingsBySeller() {
    List<Listing> sellerListings = listingService.getListingBySeller(testUser);
    assertThat(sellerListings).hasSizeGreaterThanOrEqualTo(1);
  }

  @Test
  void getListingsByListingStatus() {
    List<Listing> activeListings = listingService.getListingByListingStatus(ListingStatus.ACTIVE);
    assertThat(activeListings).hasSizeGreaterThanOrEqualTo(1);
    assertThat(activeListings.getFirst().getId()).isEqualTo(testListing.getId());
  }

  @Test
  void getListingsByCategory() {
    List<Listing> furnitureListings = listingService.getListingByCategory(Category.FURNITURE);
    assertThat(furnitureListings).hasSizeGreaterThanOrEqualTo(1);
    assertThat(furnitureListings.getFirst().getId()).isEqualTo(testListing.getId());
  }

  @Test
  void getAllListings() {
    List<Listing> allListings = listingService.getAllListings();
    assertThat(allListings).hasSizeGreaterThanOrEqualTo(1);
    assertThat(allListings.size()).isEqualTo(listingRepository.findAll().size());
  }

  @Test
  void createAndRetrieveListing() {
    Listing newListing = listingRepository.save(Listing.builder()
            .title("Test Listing")
            .description("Description")
            .price(150.0)
            .category(Category.CLOTHES)
            .status(ListingStatus.ACTIVE)
            .seller(testUser)
            .build());

    Listing savedListing = listingService.createListing(newListing);

    assertThat(savedListing.getId()).isGreaterThan(0);
    assertThat(listingService.getListingById(savedListing.getId())).isPresent();
  }

  @Test
  void updateListingStatus() {
    listingService.updateListingStatus(testListing.getId(), ListingStatus.SOLD);
    Listing updated = listingService.getListingById(testListing.getId()).orElseThrow();
    assertThat(updated.getStatus()).isEqualTo(ListingStatus.SOLD);
  }

  @Test
  void deleteListing() {
    listingService.deleteListingById(testListing.getId());
    assertThat(listingService.getListingById(testListing.getId())).isEmpty();
  }

  @Test
  void updateListing_PartialUpdate_Success() {
    Listing partialUpdate = Listing.builder()
            .title("Updated Title")
            .price(200.0)
            .build();

    listingService.updateListing(testListing.getId(), partialUpdate);
    Listing updated = listingService.getListingById(testListing.getId()).orElseThrow();

    assertThat(updated.getTitle()).isEqualTo("Updated Title");
    assertThat(updated.getPrice()).isEqualTo(200.0);
    assertThat(updated.getDescription()).isEqualTo("Original description");
    assertThat(updated.getCategory()).isEqualTo(Category.FURNITURE);
  }

  @Test
  void updateListing_Full_success() {
    Listing update = Listing.builder()
            .title("Updated Title")
            .description("Updated description")
            .price(200.0)
            .category(Category.CLOTHES)
            .status(ListingStatus.INACTIVE)
            .latitude(60.0)
            .longitude(11.0)
            .build();

    listingService.updateListing(testListing.getId(), update);
    Listing updated = listingService.getListingById(testListing.getId()).orElseThrow();

    assertThat(updated.getId()).isEqualTo(testListing.getId());
    assertThat(updated.getTitle()).isEqualTo("Updated Title");
    assertThat(updated.getDescription()).isEqualTo("Updated description");
    assertThat(updated.getPrice()).isEqualTo(200.0);
    assertThat(updated.getCategory()).isEqualTo(Category.CLOTHES);
    assertThat(updated.getStatus()).isEqualTo(ListingStatus.INACTIVE);
    assertThat(updated.getLatitude()).isEqualTo(60.0);
    assertThat(updated.getLongitude()).isEqualTo(11.0);
  }

  @Test
  void updateListing_NotFound() {
    Listing update = Listing.builder()
            .title("Non-existent")
            .build();

    Optional<Listing> result = listingService.updateListing(9999, update);
    assertThat(result).isEmpty();
  }
}
