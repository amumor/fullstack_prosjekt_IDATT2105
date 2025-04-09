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

  /**
   * Finds all bookmarks associated with a specific user.
   *
   * @param user The user whose bookmarks are to be fetched.
   * @return A list of bookmarks associated with the specified user.
   */
  List<Bookmark> findByUser(User user);

  /**
   * Finds a bookmark by its user and listing.
   *
   * @param user    The user associated with the bookmark.
   * @param listing The listing associated with the bookmark.
   * @return An Optional containing the found bookmark, or empty if not found.
   */
  Optional<Bookmark> findByUserAndListing(User user, Listing listing);
}
