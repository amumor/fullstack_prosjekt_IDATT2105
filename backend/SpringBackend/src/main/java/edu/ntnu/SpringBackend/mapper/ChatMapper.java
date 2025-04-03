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
            .buyerId(chat.getBuyer().getId())
            .sellerId(chat.getListing().getSeller().getId())
            .listingId(chat.getListing().getId())
            .createdAt(chat.getCreatedAt())
            .messages(chat.getMessages().stream()
                    .map(messageMapper::toDto)
                    .collect(Collectors.toList()))
            .build();
  }
}
