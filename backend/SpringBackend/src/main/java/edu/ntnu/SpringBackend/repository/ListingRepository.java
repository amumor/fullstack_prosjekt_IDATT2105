package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing listings in the database.
 * This interface extends JpaRepository to provide CRUD operations for Listing entities.
 *
 */
public interface ListingRepository extends JpaRepository<Listing, UUID> {
  Optional<Listing> findById(UUID id);
  List<Listing> findBySeller(User user, Pageable pageable);
  List<Listing> findByTitleContainingIgnoreCaseAndStatus(String title, ListingStatus listingStatus, Pageable pageable);
  List<Listing> findByCategoryAndStatus(Category category, ListingStatus listingStatus, Pageable pageable);
  // TODO: methods below does not work, find across multiple categories returns o listings most likely because of some pageable error...
//  List<Listing> findByCategoryInAndStatus(List<Category> all, ListingStatus listingStatus, Pageable pageable);
//  List<Listing> findByCategory_IdAndStatus(UUID categoryId, ListingStatus status, Pageable pageable);
//  List<Listing> findDistinctByCategoryInAndStatus(List<Category> categories, ListingStatus status, Pageable pageable);
}
