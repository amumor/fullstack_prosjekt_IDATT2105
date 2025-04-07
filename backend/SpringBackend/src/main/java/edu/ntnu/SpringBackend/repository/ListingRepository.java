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

public interface ListingRepository extends JpaRepository<Listing, UUID> {
  Optional<Listing> findById(UUID id);
  List<Listing> findByStatus(ListingStatus status, Pageable pageable);
  List<Listing> findByCategory(Category category, Pageable pageable);
  List<Listing> findByCategoryIn(List<Category> categories, Pageable pageable);
  List<Listing> findBySeller(User user, Pageable pageable);
  List<Listing> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
