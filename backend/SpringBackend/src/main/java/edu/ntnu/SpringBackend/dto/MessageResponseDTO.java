package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for Message responses.
 * <p>
 * This class is used to transfer message data between the client and server.
 * It contains the chat ID, sender's first and last name, content of the message, and the timestamp of when it was sent.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {

  /**
   * The unique identifier of the chat associated with the message.
   */
  private UUID chatId;

  /**
   * The first name of the sender of the message.
   */
  private String senderFirstName;

  /**
   * The last name of the sender of the message.
   */
  private String senderLastName;

  /**
   * The content of the message.
   */
  private String content;

  /**
   * The timestamp of when the message was sent.
   */
  private LocalDateTime sentAt;
}
