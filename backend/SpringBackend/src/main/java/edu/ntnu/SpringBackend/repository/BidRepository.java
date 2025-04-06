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
  Optional<Bid> findByChatAndStatus(Chat chat, BidStatus status);
  List<Bid> findByStatusAndBuyer(BidStatus status, User buyer);
}
