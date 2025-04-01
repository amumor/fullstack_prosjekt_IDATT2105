package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.model.User;

public class UserMapper {

  public static User toEntity(UserRequestDTO dto) {
    if (dto == null) return null;

    return User.builder()
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .email(dto.getEmail())
            .password(dto.getPassword())
            .phoneNumber(dto.getPhoneNumber())
            .role(dto.getRole())
            .build();
  }

  public static UserResponseDTO toDto(User user) {
    if (user == null) return null;

    return UserResponseDTO.builder()
            .id(String.valueOf(user.getId()))
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .role(user.getRole().name())
            .build();
  }
}
