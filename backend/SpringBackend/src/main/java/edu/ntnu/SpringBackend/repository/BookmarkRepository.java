package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookmarkRepository extends JpaRepository<Bookmark, UUID> {
  List<Bookmark> findByUser(User user);
  List<Bookmark> findByListing(Listing listing);
  Optional<Bookmark> findByUserAndListing(User user, Listing listing);
}
