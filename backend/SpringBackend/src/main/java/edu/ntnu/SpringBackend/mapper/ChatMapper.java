package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.ChatResponseDTO;
import edu.ntnu.SpringBackend.model.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper class for converting Chat entities to ChatResponseDTOs.
 * <p>
 * This class is responsible for mapping the fields of a Chat entity to a ChatResponseDTO.
 * It uses the Builder pattern to create instances of ChatResponseDTO.
 * </p>
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ChatMapper {

  private final MessageMapper messageMapper;
  private final BidMapper bidMapper;

  /**
   * Converts a Chat entity to a ChatResponseDTO.
   *
   * @param chat The Chat entity to convert.
   * @return A ChatResponseDTO containing the mapped fields from the Chat entity.
   */
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
            .bids(chat.getBids().stream()
                    .map(bidMapper::toDto)
                    .collect(Collectors.toList()))
            .build();
  }
}
