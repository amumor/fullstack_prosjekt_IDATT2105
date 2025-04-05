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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidService {
  private final Logger logger = LoggerFactory.getLogger(BidService.class);
  private final BidRepository bidRepository;
  private final ChatRepository chatRepository;

  @Transactional
  public Bid placeBid(UUID chatId, User user, BidRequestDTO request) {
    logger.info("> Handling bid placement");

    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

    if (!user.getEmail().equals(chat.getBuyer().getEmail())) {
      logger.error("!!! User {} is not the buyer of chat", user.getEmail());
      throw new IllegalArgumentException("User is not the buyer of this chat");
    }

    Listing listing = chat.getListing();
    if (!listing.getStatus().equals(ListingStatus.ACTIVE)) {
      logger.error("!!! Listing {} is not active anymore", listing.getId());
      throw new IllegalStateException("Listing is not active anymore.");
    }

    bidRepository.findByChatAndStatus(chat, BidStatus.PENDING)
            .ifPresent(bid -> {
              logger.error("!!! Bid already exists for chat {}", chat.getId());
              throw new IllegalStateException("Bid already exists for this chat");
            });

    Double price = request.getPrice();
    if (price < 0) {
      logger.error("!!! Price cannot be negative");
      throw new IllegalArgumentException("Price cannot be negative");
    }

    Bid bid = Bid.builder()
            .chat(chat)
            .buyer(user)
            .price(price)
            .status(BidStatus.PENDING)
            .build();
    bidRepository.save(bid);

    logger.info("> Bid created with id {}", bid.getId());
    return bid;
  }
}
