package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for chat responses.
 * This class is used to transfer data from the server to the client when retrieving chats.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDTO {
  private UUID id;
  private UUID listingId;
  private String buyerFirstName;
  private String buyerLastName;
  private String sellerFirstName;
  private String sellerLastName;
  private LocalDateTime createdAt;
  private List<MessageResponsetDTO> messages;
}
