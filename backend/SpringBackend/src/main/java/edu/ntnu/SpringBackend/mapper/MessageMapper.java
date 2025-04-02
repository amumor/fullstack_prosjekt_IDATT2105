package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.MessageDTO;
import edu.ntnu.SpringBackend.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
  public MessageDTO toDto(Message message) {
    return MessageDTO.builder()
            .id(message.getId())
            .chatId(message.getChat().getId())
            .senderId(message.getSender().getId())
            .content(message.getContent())
            .sentAt(message.getSentAt())
            .build();
  }
}
