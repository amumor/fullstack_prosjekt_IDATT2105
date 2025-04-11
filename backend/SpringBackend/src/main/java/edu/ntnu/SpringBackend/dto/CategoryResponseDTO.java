package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for a category response.
 * This class is used to transfer data from the server to the client when retrieving a category.
 * It contains the necessary fields, an id and a name, to represent a category.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {

  /**
   * The unique identifier of the category.
   */
  private UUID id;

  /**
   * The name of the category.
   */
  private String name;
}
