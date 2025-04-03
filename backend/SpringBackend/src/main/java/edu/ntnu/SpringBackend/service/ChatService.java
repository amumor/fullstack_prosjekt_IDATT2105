package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.ChatRequestDTO;
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

@Service
@RequiredArgsConstructor
public class ChatService {
  private final Logger logger = LoggerFactory.getLogger(ChatService.class);
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;
  private final UserService userService;
  private final ListingService listingService;

  @Transactional
  public Chat createOrGetChat(ChatRequestDTO requestDTO) {
    logger.info("> Creating chat");
    User buyer = userService.getUserByEmail(requestDTO.getEmail());
    Listing listing = listingService.getListingById(requestDTO.getListingId());

    logger.info("> Listing: {}", listing);
    logger.info("> Buyer: {}", buyer);
    return chatRepository.findByBuyerAndListing(buyer, listing)
            .orElseGet(() -> chatRepository.save(Chat.builder()
                    .buyer(buyer)
                    .listing(listing)
                    .createdAt(LocalDateTime.now())
                    .build()));
  }


  @Transactional
  public Message addMessageToChat(UUID chatId, String senderEmail, String content) {
    logger.info("> Adding message to chat");
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));
    User sender = userService.getUserByEmail(senderEmail);

    Message message = Message.builder()
            .chat(chat)
            .sender(sender)
            .content(content)
            .sentAt(LocalDateTime.now())
            .build();

    logger.info("> Message sent");
    return messageRepository.save(message);
  }

  @Transactional(readOnly=true)
  public List<Message> getMessages(UUID chatId) {
    logger.info("> Fetching messages for chat {}", chatId);
    List<Message> messages = messageRepository.findByChatOrderBySentAtAsc(
            chatRepository.findById(chatId)
                    .orElseThrow(() -> new NoSuchElementException("Chat not found"))
    );

    logger.info("> Messages found");
    return new ArrayList<>(messages);
  }

  public Chat getChat(UUID chatId) {
    logger.info("> Fetching chat {}", chatId);
    return chatRepository.findById(chatId)
            .orElseThrow(() -> new NoSuchElementException("Chat not found"));
  }
}
