package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * Data Transfer Object (DTO) for Chat responses.
 * <p>
 * This class is used to transfer chat data between the client and server.
 * It contains the ID, listing ID, buyer and seller names, timestamp, messages, and bids of the chat.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDTO {

  /**
   * The unique identifier of the chat.
   */
  private UUID id;

  /**
   * The unique identifier of the listing associated with the chat.
   */
  private UUID listingId;

  /**
   * The first name of the buyer associated with the chat.
   */
  private String buyerFirstName;

  /**
   * The last name of the buyer associated with the chat.
   */
  private String buyerLastName;

  /**
   * The first name of the seller associated with the chat.
   */
  private String sellerFirstName;

  /**
   * The last name of the seller associated with the chat.
   */
  private String sellerLastName;

  /**
   * The timestamp of when the chat was created.
   */
  private LocalDateTime createdAt;

  /**
   * A list of messages associated with the chat.
   */
  private List<MessageResponseDTO> messages;

  /**
   * A list of bids associated with the chat.
   */
  private List<BidResponseDTO> bids;
}
