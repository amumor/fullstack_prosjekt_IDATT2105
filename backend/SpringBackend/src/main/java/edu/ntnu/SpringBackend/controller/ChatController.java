package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ChatDTO;
import edu.ntnu.SpringBackend.dto.MessageDTO;
import edu.ntnu.SpringBackend.mapper.ChatMapper;
import edu.ntnu.SpringBackend.mapper.MessageMapper;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.ChatService;
import edu.ntnu.SpringBackend.service.ListingService;
import edu.ntnu.SpringBackend.service.MessageService;
import edu.ntnu.SpringBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {
  private final Logger logger = LoggerFactory.getLogger(ChatController.class);
  private final ChatService chatService;
  private final ChatMapper chatMapper;
  private final MessageMapper messageMapper;
  private final UserService userService;

  @PostMapping("/listings/{listingId}")
  public ResponseEntity<ChatDTO> createOrGetChat(
          @PathVariable UUID listingId,
          UUID userId) {
    return ResponseEntity.ok(chatMapper.toDto(chatService.createOrGetChat(listingId, userId)));
  }

  @GetMapping("/{chatId}/messages")
  public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable UUID chatId) {
    logger.info("Received request to get messages for chat {}", chatId);
    List<MessageDTO> messages = chatService.getMessages(chatId).stream()
            .map(messageMapper::toDto)
            .collect(Collectors.toList());
    return ResponseEntity.ok(messages);
  }
  
}

