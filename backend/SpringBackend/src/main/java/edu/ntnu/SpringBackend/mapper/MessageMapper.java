package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.MessageResponsetDTO;
import edu.ntnu.SpringBackend.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
  public MessageResponsetDTO toDto(Message message) {
    return MessageResponsetDTO.builder()
            .id(message.getId())
            .chatId(message.getChat().getId())
            .senderId(message.getSender().getId())
            .content(message.getContent())
            .sentAt(message.getSentAt())
            .build();
  }
}
