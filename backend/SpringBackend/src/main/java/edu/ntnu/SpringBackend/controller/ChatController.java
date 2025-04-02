package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ChatRequestDTO;
import edu.ntnu.SpringBackend.dto.ChatResponseDTO;
import edu.ntnu.SpringBackend.dto.MessageResponsetDTO;
import edu.ntnu.SpringBackend.mapper.ChatMapper;
import edu.ntnu.SpringBackend.mapper.MessageMapper;
import edu.ntnu.SpringBackend.service.ChatService;
import edu.ntnu.SpringBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ChatResponseDTO> createOrGetChat(
          @RequestBody ChatRequestDTO chatRequestDTO
  ) {
    logger.info("Received POST request to create or get chat");
    return ResponseEntity.ok(chatMapper.toDto(chatService.createOrGetChat(chatRequestDTO)));
  }

  @GetMapping("/{chatId}/messages")
  public ResponseEntity<List<MessageResponsetDTO>> getMessages(@PathVariable UUID chatId) {
    logger.info("Received request to get messages for chat {}", chatId);
    List<MessageResponsetDTO> messages = chatService.getMessages(chatId).stream()
            .map(messageMapper::toDto)
            .collect(Collectors.toList());
    return ResponseEntity.ok(messages);
  }
  
}

