package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new category.
 * This class is used to transfer data from the client to the server when creating a new category.
 * It contains the necessary field, a name, to create a category.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreationRequestDTO {

  /**
   * The name of the category to be created.
   */
  private String name;
}
