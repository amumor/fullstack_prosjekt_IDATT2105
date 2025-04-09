package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.MessageResponseDTO;
import edu.ntnu.SpringBackend.model.Message;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting Message entities to DTOs.
 * This class is responsible for transforming data between the model and the DTO layer.
 * It provides methods to convert a single Message entity to a MessageResponseDTO.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @see MessageResponseDTO
 * @since 1.0
 */
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
