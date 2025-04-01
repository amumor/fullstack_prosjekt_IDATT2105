package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {
  List<Listing> findBySeller(User user);
  Optional<Listing> findById(UUID id);
  List<Listing> findByStatus(ListingStatus status);
  List<Listing> findByCategory(Category category);
}
