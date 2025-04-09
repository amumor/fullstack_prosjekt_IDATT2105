package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Chat entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
public interface ChatRepository extends JpaRepository<Chat, UUID> {

  /**
   * Finds all chats associated with a specific listing.
   *
   * @param listing The listing whose chats are to be fetched.
   * @return A list of chats associated with the specified listing.
   */
  List<Chat> findByListing(Listing listing);

  /**
   * Finds a chat by its buyer and listing.
   *
   * @param user    The buyer associated with the chat.
   * @param listing The listing associated with the chat.
   * @return An Optional containing the found chat, or empty if not found.
   */
  Optional<Chat> findByBuyerAndListing(User user, Listing listing);

  /**
   * Finds a chat by its buyer and seller.
   *
   * @param buyer  The buyer associated with the chat.
   * @param seller The seller associated with the chat.
   * @return A list of chats associated with the specified buyer and seller.
   */
  List<Chat> findByBuyerOrListing_Seller(User buyer, User seller);

  /**
   * Finds a chat by its listing and buyer.
   *
   * @param listing The listing associated with the chat.
   * @param buyer   The buyer associated with the chat.
   * @return An Optional containing the found chat, or empty if not found.
   */
  boolean existsByListingAndBuyer(Listing listing, User buyer);
}
