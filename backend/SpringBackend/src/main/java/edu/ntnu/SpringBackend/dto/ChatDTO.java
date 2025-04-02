package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

  private UUID id;
  private UUID buyerId;
  private UUID sellerId;
  private UUID listingId;
  private LocalDateTime createdAt;
  private List<MessageDTO> messages;
}
