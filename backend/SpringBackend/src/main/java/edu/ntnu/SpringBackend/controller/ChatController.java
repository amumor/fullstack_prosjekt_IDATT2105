package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ChatResponseDTO;
import edu.ntnu.SpringBackend.dto.MessageRequestDTO;
import edu.ntnu.SpringBackend.dto.MessageResponsetDTO;
import edu.ntnu.SpringBackend.mapper.ChatMapper;
import edu.ntnu.SpringBackend.mapper.MessageMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatController {
  private final Logger logger = LoggerFactory.getLogger(ChatController.class);
  private final ChatService chatService;
  private final ChatMapper chatMapper;
  private final MessageMapper messageMapper;

  /**
   * Get a chat between a buyer and a listing.
   *
   * @param listingId listing id of the listing
   * @param user      the user(buyer) who is trying to get the chat
   * @return the chat between the buyer and the listing
   */
  @GetMapping("/listing/{listingId}/chat")
  public ResponseEntity<ChatResponseDTO> getChat(
          @PathVariable UUID listingId,
          @AuthenticationPrincipal User user
  ) {
    logger.info("GET request received on [/api/v1/listings/{}/chats]", listingId);
    return ResponseEntity.ok(chatMapper.toDto(chatService.getChatFromBuyerAndListing(listingId, user)));
  }

  /**
   * Get all chats for a listing for a seller.
   *
   * @param user the user who is trying to get the chats
   * @return all chats for the user
   */
  @GetMapping("/listing/{listingId}/chats")
  public ResponseEntity<List<ChatResponseDTO>> getAllChatsForListing(
          @PathVariable UUID listingId,
          @AuthenticationPrincipal User user
  ) {
    logger.info("GET request received for seller to see chats for listing {}", listingId);
    return ResponseEntity.ok(chatService.getAllChatsForListing(listingId, user).stream()
            .map(chatMapper::toDto)
            .collect(Collectors.toList()));
  }

  /**
   * Get all chats for a user, either buyer or seller.
   *
   * @param user the user who is trying to get the chats
   * @return all chats for the user
   */
  @GetMapping("/user/my-chats")
  public ResponseEntity<List<ChatResponseDTO>> getAllChatsForUser(
          @AuthenticationPrincipal User user
  ) {
    logger.info("GET request received for user to see all chats");
    return ResponseEntity.ok(chatService.getAllChatsForUser(user).stream()
            .map(chatMapper::toDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a chat between a buyer and a listing.
   *
   * @param buyer buyer who is trying to create the chat
   * @param listingId listing id of the listing
   * @param messageRequestDTO the chat request dto
   * @return the chat between the buyer and the listing
   */
  @PostMapping("/listing/{listingId}/create")
  public ResponseEntity<ChatResponseDTO> createChatFromBuyer(
          @AuthenticationPrincipal User buyer,
          @PathVariable UUID listingId,
          @RequestBody MessageRequestDTO messageRequestDTO
  ) {
    logger.info("POST request received to create chat");
    logger.info("> {}", messageRequestDTO.getContent());
    return ResponseEntity.ok(chatMapper.toDto(chatService.createChat(buyer, listingId, messageRequestDTO)));
  }

  /**
   * Add a message to a chat.
   *
   * @param chatId the id of the chat
   * @param user the user who is trying to add the message
   * @param messageRequestDTO the message request dto
   * @return the message response dto
   */
  @PostMapping("/chat/{chatId}/message")
  public ResponseEntity<MessageResponsetDTO> addMessageToChat(
          @PathVariable UUID chatId,
          @AuthenticationPrincipal User user,
          @RequestBody MessageRequestDTO messageRequestDTO
  ) {
    logger.info("POST request received to add message to chat {}", chatId);
    return ResponseEntity.ok(messageMapper.toDto(chatService.sendMessageToChat(chatId, user, messageRequestDTO)));
  }
}

