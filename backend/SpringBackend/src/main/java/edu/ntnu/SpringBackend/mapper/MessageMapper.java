package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.MessageResponseDTO;
import edu.ntnu.SpringBackend.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
  public MessageResponseDTO toDto(Message message) {
    return MessageResponseDTO.builder()
            .chatId(message.getChat().getId())
            .senderFirstName(message.getSender().getFirstName())
            .senderLastName(message.getSender().getLastName())
            .content(message.getContent())
            .sentAt(message.getSentAt())
            .build();
  }
}
