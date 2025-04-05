package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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
