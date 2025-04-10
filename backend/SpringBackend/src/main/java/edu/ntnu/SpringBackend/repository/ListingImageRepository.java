package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing ListingImage entities.
 * This interface extends JpaRepository to provide CRUD operations for ListingImage entities.
 * It also includes custom query methods for finding images by listing.
 * <p>
 * JPA (Java Persistence API) is a specification for accessing, persisting,
 * and managing data between Java objects and relational databases.
 * It provides a standard way to map Java objects to database tables and vice versa.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
public interface ListingImageRepository extends JpaRepository<ListingImage, UUID> {
  List<ListingImage> findByListing(Listing listing);
  List<ListingImage> findByListingId(UUID listingId);
}
