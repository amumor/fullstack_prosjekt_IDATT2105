package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

  private UUID id;
  private UUID senderId;
  private UUID chatId;
  private String content;
  private LocalDateTime sentAt;
}
