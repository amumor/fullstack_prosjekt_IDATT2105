package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for message responses.
 * This class is used to transfer data from the server to the client when retrieving messages.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
  private UUID chatId;
  private String senderFirstName;
  private String senderLastName;
  private String content;
  private LocalDateTime sentAt;
}
