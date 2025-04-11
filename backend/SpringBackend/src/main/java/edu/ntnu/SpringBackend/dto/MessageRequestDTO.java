package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a message request.
 * This class is used to transfer data from the client to the server when creating or updating a message.
 * It contains the necessary fields, a content, to represent a message.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDTO {

  /**
   * The content of the message.
   */
  private String content;
}
