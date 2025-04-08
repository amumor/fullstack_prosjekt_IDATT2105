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
  private UUID id;
  private UUID chatId;
  private Double price;
  private BidStatus status;
  private LocalDateTime timestamp;
}
