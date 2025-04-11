package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.BidRequestDTO;
import edu.ntnu.SpringBackend.model.Bid;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.BidStatus;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.repository.BidRepository;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for handling bids.
 * This class contains methods for placing, accepting, rejecting, and canceling bids.
 * It also contains a helper method to validate the bid based on the chat and user.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class BidService {
  private final Logger logger = LoggerFactory.getLogger(BidService.class);
  private final BidRepository bidRepository;
  private final ChatRepository chatRepository;
  private final ListingService listingService;

  /**
   * Get all accepted bids for a user.
   *
   * @param user the user for whom to get the accepted bids
   * @return the list of bids for the user
   */
  public List<Bid> getAcceptedBidsForUser(User user) {
    return bidRepository.findByStatusAndBuyer(BidStatus.ACCEPTED, user);
  }

  /**
   * Place a bid on a chat.
   * The buyer can only place one pending bid at a time.
   *
   * @param chatId  the id of the chat
   * @param user    the user who is placing the bid
   * @param request the bid request
   * @return the bid response
   */
  @Transactional
  public Bid placeBid(UUID chatId, User user, BidRequestDTO request) {
    logger.info("> Handling bid placement");

    validateBid(chatId, user, true);

    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

    bidRepository.findByChatAndStatus(chat, BidStatus.PENDING)
            .ifPresent(bid -> {
              logger.error("!!! Pending bid already exists for chat {}", chat.getId());
              throw new IllegalStateException("Pending bid already exists for this chat");
            });

    Bid bid = Bid.builder()
            .chat(chat)
            .buyer(user)
            .price(request.getPrice())
            .status(BidStatus.PENDING)
            .build();
    bidRepository.save(bid);

    logger.info("> Bid created with id {}", bid.getId());
    return bid;
  }

  /**
   * Accept a bid.
   * Only the seller of the listing can accept a bid.
   *
   * @param bidId the id of the bid
   * @param user  the user who is accepting the bid
   * @return the bid response
   */
  @Transactional
  public Bid acceptBid(UUID bidId, User user) {
    logger.info("> Handling bid accept");
    Bid bid = bidRepository.findById(bidId)
            .orElseThrow(() -> new IllegalArgumentException("Bid not found"));

    validateBid(bid.getChat().getId(), user, false);

    bid.setStatus(BidStatus.ACCEPTED);
    bidRepository.save(bid);
    listingService.updateListingStatus(bid.getChat().getListing().getId(), ListingStatus.SOLD);

    logger.info("> Bid accepted with id {}", bid.getId());

    return bid;
  }

  /**
   * Reject a bid.
   * Only the seller of the listing can reject a bid.
   *
   * @param bidId the id of the bid
   * @param user  the user who is rejecting the bid
   * @return the bid response
   */
  @Transactional
  public Bid rejectBid(UUID bidId, User user) {
    logger.info("> Handling bid reject");
    Bid bid = bidRepository.findById(bidId)
            .orElseThrow(() -> new IllegalArgumentException("Bid not found"));

    validateBid(bid.getChat().getId(), user, false);

    bid.setStatus(BidStatus.REJECTED);
    bidRepository.save(bid);

    logger.info("> Bid rejected with id {}", bid.getId());

    return bid;
  }

  /**
   * Cancel a bid.
   * Only the buyer of the bid can cancel a bid.
   *
   * @param bidId the id of the bid
   * @param user  the user who is canceling the bid
   * @return the bid response
   */
  @Transactional
  public Bid cancelBid(UUID bidId, User user) {
    logger.info("> Handling bid cancel");
    Bid bid = bidRepository.findById(bidId)
            .orElseThrow(() -> new IllegalArgumentException("Bid not found"));

    validateBid(bid.getChat().getId(), user, true);

    if (bid.getStatus() != BidStatus.PENDING) {
      logger.error("!!! Bid {} is not pending anymore", bid.getId());
      throw new IllegalStateException("Bid is not pending anymore.");
    }

    bid.setStatus(BidStatus.CANCELED);
    bidRepository.save(bid);

    logger.info("> Bid canceled with id {}", bid.getId());

    return bid;
  }

  /**
   * Helper method to validate the bid based on the chat and user.
   * This method checks if the listing is active and if the user is either the buyer or seller of the chat.
   *
   * @param chatId  the id of the chat
   * @param user    the user who is placing the bid
   * @param isBuyer true if the action is from the buyer, false if from the seller
   */
  private void validateBid(UUID chatId, User user, boolean isBuyer) {
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

    Listing listing = chat.getListing();
    if (!listing.getStatus().equals(ListingStatus.ACTIVE)) {
      logger.error("!!! Listing {} is not active anymore", listing.getId());
      throw new IllegalStateException("Listing is not active anymore.");
    }

    if (isBuyer) {
      if (!user.getEmail().equals(chat.getBuyer().getEmail())) {
        logger.error("!!! User {} is not the buyer of chat", user.getEmail());
        throw new IllegalArgumentException("User is not the buyer of this chat");
      }
    } else {
      if (!user.getEmail().equals(listing.getSeller().getEmail())) {
        logger.error("!!! User {} is not the seller of listing {}", user.getEmail(), listing.getId());
        throw new IllegalArgumentException("User is not the seller of this listing");
      }
    }
  }
}
