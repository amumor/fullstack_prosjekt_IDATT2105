package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.MessageRequestDTO;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import edu.ntnu.SpringBackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final Logger logger = LoggerFactory.getLogger(ChatService.class);
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;
  private final ListingService listingService;

  /**
   * Get a chat between a buyer and a listing.
   *
   * @param listingId the listing id of the listing
   * @param user the user who is trying to get the chat
   * @return the chat between the buyer and the listing
   */
  public Chat getChatFromBuyerAndListing(UUID listingId, User user) {
    logger.info("> Fetching chat for listing {} and user {}", listingId, user.getEmail());
    Listing listing = listingService.getListingById(listingId);
    Chat chat = chatRepository.findByBuyerAndListing(user, listing)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));
    logger.info("> Chat found");
    return chat;
  }

  /**
   * Get all chats for a listing for a seller.
   *
   * @param listingId the listing id of the listing
   * @param user the user who is trying to get the chats
   * @return all chats for the user
   */
  public List<Chat> getAllChatsForListing(UUID listingId, User user) {
    logger.info("> Fetching all chats for listing {}", listingId);

    Listing listing = listingService.getListingById(listingId);
    if (!listing.getSeller().getEmail().equals(user.getEmail())) {
      logger.error("!!! User {} is not the seller of listing {}", user.getEmail(), listingId);
      throw new AccessDeniedException("User is not the seller of this listing");
    }

    List<Chat> chats = chatRepository.findByListing(listing);
    if (chats.isEmpty()) {
      logger.warn("> No chats found for listing {}", listingId);
      throw new NoSuchElementException("No chats found for this listing");
    }
    logger.info("> Chats found for listing {}", listingId);
    return chats;
  }

  /**
   * Get all chats for a user.
   * All chats were the user is either the buyer or the seller is returned in a set.
   *
   * @param user the user who is trying to get the chats
   * @return all chats for the user
   */
  public Collection<Chat> getAllChatsForUser(User user) {
    logger.info("> Fetching all chats for user {}", user.getEmail());
    List<Chat> allChats = chatRepository.findByBuyerOrListing_Seller(user, user);

    if (allChats.isEmpty()) {
      logger.warn("> No chats found for user {}", user.getEmail());
      throw new NoSuchElementException("No chats found for this user");
    }

    logger.info("> Chats found for user {}", user.getEmail());
    return allChats;
  }

  /**
   * Create a chat between a buyer and a listing.
   *
   * @param buyer the buyer who is trying to create the chat
   * @param listingId the listing id of the listing
   * @param messageRequestDTO the chat request dto
   * @return the chat between the buyer and the listing
   */
  @Transactional
  public Chat createChat(User buyer, UUID listingId, MessageRequestDTO messageRequestDTO) {
    logger.info("> Creating chat for buyer {} on listing {}", buyer.getEmail(), listingId);

    logger.info(">, {}", messageRequestDTO.getContent());
    if (messageRequestDTO.getContent() == null || messageRequestDTO.getContent().trim().isEmpty()) {
      logger.error("!!! Cannot create chat without an initial message");
      throw new IllegalArgumentException("Initial message must not be empty.");
    }

    Listing listing = listingService.getListingById(listingId);
    if (chatRepository.existsByListingAndBuyer(listing, buyer)) {
      logger.error("!!! Chat already exists for buyer {} and listing {}", buyer.getEmail(), listingId);
      throw new IllegalArgumentException("Chat already exists for this listing.");
    }
    if (buyer.getEmail().equals(listing.getSeller().getEmail())) {
      logger.error("!!! Seller {} cannot initiate chat on their own listing {}", buyer.getEmail(), listingId);
      throw new AccessDeniedException("Sellers cannot initiate chats on their own listings.");
    }

    Chat chat = Chat.builder()
            .buyer(buyer)
            .listing(listing)
            .createdAt(LocalDateTime.now())
            .build();
    chat = chatRepository.save(chat);
    sendMessageToChat(chat.getId(), buyer, messageRequestDTO);
    return chat;
  }

  /**
   * Send a message to a chat.
   *
   * @param chatId the id of the chat
   * @param user the user who is trying to send the message
   * @param messageRequestDTO the message request dto
   * @return the message response dto
   */
  @Transactional
  public Message sendMessageToChat(UUID chatId, User user, MessageRequestDTO messageRequestDTO) {
    logger.info("> Sending message to chat {}", chatId);
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));
    if (isParticipant(chatId, user)) {
      throw new AccessDeniedException("User is not a participant in this chat");
    }

    Message message = Message.builder()
            .chat(chat)
            .sender(user)
            .content(messageRequestDTO.getContent())
            .sentAt(LocalDateTime.now())
            .build();
    chat.getMessages().add(message); // Add a message to the ArrayList
    return messageRepository.save(message);
  }

  /**
   * Check if the user is a participant in the chat.
   *
   * @param chatId the id of the chat
   * @param user the user who is trying to check if they are a participant
   * @return true if the user is a participant, false otherwise
   */
  public boolean isParticipant(UUID chatId, User user) {
    String currentEmail = user.getEmail();
    logger.info("> Checking participant for chat {}", chatId);
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));

    String buyerEmail = chat.getBuyer().getEmail();
    String sellerEmail = chat.getListing().getSeller().getEmail();
    return !currentEmail.equals(buyerEmail) && !currentEmail.equals(sellerEmail);
  }

  public Chat getChatById(UUID chatId) {
    logger.info("> Fetching chat for chatId {}", chatId);
    return chatRepository.findById(chatId)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));
  }
}
