package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for Bid responses.
 * <p>
 * This class is used to transfer bid data between the client and server.
 * It contains the ID, chat ID, price, status, and timestamp of the bid.
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
public class BidResponseDTO {

  /**
   * The unique identifier of the bid.
   * This field is automatically generated and cannot be null.
   */
  private UUID id;

  /**
   * The unique identifier of the chat associated with the bid.
   * This field cannot be null.
   */
  private UUID chatId;

  /**
   * The price of the bid.
   * This field cannot be null.
   */
  private Double price;

  /**
   * The status of the bid.
   * This field cannot be null.
   */
  private BidStatus status;

  /**
   * The timestamp of when the bid was created.
   * This field cannot be null.
   */
  private LocalDateTime timestamp;
}
