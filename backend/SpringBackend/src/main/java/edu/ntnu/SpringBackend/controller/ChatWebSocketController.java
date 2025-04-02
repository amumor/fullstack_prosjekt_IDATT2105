package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.MessageDTO;
import edu.ntnu.SpringBackend.dto.WebSocketMessage;
import edu.ntnu.SpringBackend.mapper.MessageMapper;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
  private final Logger logger = LoggerFactory.getLogger(ChatWebSocketController.class);
  private final ChatService chatService;
  private final MessageMapper messageMapper;

  @MessageMapping("/chat/{chatId}")
  @SendTo("/topic/chat/{chatId}")
  public MessageDTO handleMessage(@DestinationVariable UUID chatId, WebSocketMessage message, Principal principal) {
    logger.info("Received websocket message [{}]", message);
    Message msg = chatService.addMessageToChat(chatId, principal.getName(), message.getContent());
    return messageMapper.toDto(msg);
  }
}

