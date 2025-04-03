package edu.ntnu.SpringBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatRequestDTO {
  private UUID listingId;
  private String email;
}
