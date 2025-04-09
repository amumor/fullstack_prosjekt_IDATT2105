package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Bid;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Bid entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
public interface BidRepository extends JpaRepository<Bid, UUID> {

  /**
   * Finds a bid by its chat and status.
   *
   * @param chat   The chat associated with the bid.
   * @param status The status of the bid.
   * @return An Optional containing the found bid, or empty if not found.
   */
  Optional<Bid> findByChatAndStatus(Chat chat, BidStatus status);

  /**
   * Finds all bids by their status and buyer.
   *
   * @param status The status of the bids.
   * @param buyer  The buyer associated with the bids.
   * @return A list of bids matching the given status and buyer.
   */
  List<Bid> findByStatusAndBuyer(BidStatus status, User buyer);
}
