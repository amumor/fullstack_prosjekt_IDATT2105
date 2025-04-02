package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.ChatDTO;
import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import edu.ntnu.SpringBackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final Logger logger = LoggerFactory.getLogger(ChatService.class);
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;
  private final UserService userService;
  private final ListingService listingService;

  @Transactional
  public Chat createOrGetChat(UUID buyerId, UUID listingId) {
    logger.info("Received request to create or get chat");
    logger.info("> BuyerId: {} ", buyerId);
    logger.info("> ListingId: {} ", listingId);
    User buyer = userService.getUserById(buyerId);
    Listing listing = listingService.getListingById(listingId);

    return chatRepository.findByBuyerAndListing(buyer, listing)
            .orElseGet(() -> chatRepository.save(Chat.builder()
                    .buyer(buyer)
                    .listing(listing)
                    .createdAt(LocalDateTime.now())
                    .build()));
  }

  @Transactional
  public Message addMessageToChat(UUID chatId, String senderEmail, String content) {
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));
    User sender = userService.getUserByEmail(senderEmail);

    Message message = Message.builder()
            .chat(chat)
            .sender(sender)
            .content(content)
            .sentAt(LocalDateTime.now())
            .build();

    return messageRepository.save(message);
  }

  @Transactional(readOnly=true)
  public List<Message> getMessages(UUID chatId) {
    List<Message> messages = messageRepository.findByChatOrderBySentAtAsc(
            chatRepository.findById(chatId)
                    .orElseThrow(() -> new NoSuchElementException("Chat not found"))
    );

    return new ArrayList<>(messages);
  }
}
