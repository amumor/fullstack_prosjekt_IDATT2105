package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for message requests.
 * This class is used to transfer data from the client to the server when sending a message.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDTO {
  private String content;
}
