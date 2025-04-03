package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Bookmark;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Bookmark entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, UUID> {
  List<Bookmark> findByUser(User user);
  Optional<Bookmark> findByUserAndListing(User user, Listing listing);
}
