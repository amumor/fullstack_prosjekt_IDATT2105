package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.ChatResponseDTO;
import edu.ntnu.SpringBackend.model.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {

  private final MessageMapper messageMapper;

  public ChatResponseDTO toDto(Chat chat) {
    return ChatResponseDTO.builder()
            .id(chat.getId())
            .listingId(chat.getListing().getId())
            .buyerFirstName(chat.getBuyer().getFirstName())
            .buyerLastName(chat.getBuyer().getLastName())
            .sellerFirstName(chat.getListing().getSeller().getFirstName())
            .sellerLastName(chat.getListing().getSeller().getLastName())
            .createdAt(chat.getCreatedAt())
            .messages(chat.getMessages().stream()
                    .map(messageMapper::toDto)
                    .collect(Collectors.toList()))
            .build();
  }
}
