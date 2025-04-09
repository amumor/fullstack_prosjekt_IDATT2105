package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.model.User;

/**
 * UserMapper is a utility class that provides methods to convert User objects
 * to UserResponseDTO objects and vice versa.
 * <p>
 * This class is used to encapsulate the mapping logic between the User model
 * and the UserResponseDTO data transfer object (DTO).
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
public class UserMapper {

  /**
   * Converts a User object to a UserResponseDTO object.
   *
   * @param user the User object to convert
   * @return the converted UserResponseDTO object
   */
  public static UserResponseDTO toDto(User user) {
    if (user == null) return null;

    return UserResponseDTO.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .role(user.getRole())
            .build();
  }
}
