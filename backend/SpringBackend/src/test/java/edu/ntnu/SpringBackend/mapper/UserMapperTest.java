package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

  @Test
  void testToDtoWithNullUser() {
    assertNull(UserMapper.toDto(null));
  }

  @Test
  void testToDtoWithValidUser() {
    User user = User.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    UserResponseDTO dto = UserMapper.toDto(user);

    // Verify that each field was mapped correctly.
    assertNotNull(dto);
    assertEquals(user.getFirstName(), dto.getFirstName());
    assertEquals(user.getLastName(), dto.getLastName());
    assertEquals(user.getEmail(), dto.getEmail());
    assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
    assertEquals(user.getRole(), dto.getRole());
  }
}
