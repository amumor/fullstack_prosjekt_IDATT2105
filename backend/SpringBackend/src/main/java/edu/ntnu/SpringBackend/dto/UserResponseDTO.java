package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserResponseDTO is a data transfer object (DTO) used for user response data.
 * It contains the user's first name, last name, email, phone number, and role.
 * <p>
 * This class is used to encapsulate the user response data
 * and is typically used in the response of user-related operations.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

  /**
   * The first name of the user.
   */
  private String firstName;

  /**
   * The last name of the user.
   */
  private String lastName;

  /**
   * The email address of the user.
   */
  private String email;

  /**
   * The phone number of the user.
   */
  private String phoneNumber;

  /**
   * The role of the user.
   */
  private Role role;
}
