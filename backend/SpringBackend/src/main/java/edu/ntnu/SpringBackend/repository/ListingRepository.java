package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Integer> {
  List<Listing> findBySeller(User user);
  List<Listing> findByListingId(int id);
  List<Listing> findByStatus(ListingStatus status);
}
