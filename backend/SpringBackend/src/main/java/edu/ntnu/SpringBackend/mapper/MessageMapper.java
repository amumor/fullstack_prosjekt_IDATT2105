package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.MessageResponsetDTO;
import edu.ntnu.SpringBackend.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
  public MessageResponsetDTO toDto(Message message) {
    return MessageResponsetDTO.builder()
            .chatId(message.getChat().getId())
            .senderFirstName(message.getSender().getFirstName())
            .senderLastName(message.getSender().getLastName())
            .content(message.getContent())
            .sentAt(message.getSentAt())
            .build();
  }
}
