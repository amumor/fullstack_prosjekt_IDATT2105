package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDTOTest {

  @Test
  void testBuilderAndGetters() {
    UserResponseDTO dto = UserResponseDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    assertEquals("John", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
    assertEquals("john@example.com", dto.getEmail());
    assertEquals("12345678", dto.getPhoneNumber());
    assertEquals(Role.ROLE_USER, dto.getRole());
  }

  @Test
  void testNoArgsConstructorAndSetters() {
    UserResponseDTO dto = new UserResponseDTO();
    dto.setFirstName("Jane");
    dto.setLastName("Doe");
    dto.setEmail("jane@example.com");
    dto.setPhoneNumber("87654321");
    dto.setRole(Role.ROLE_USER);

    assertEquals("Jane", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
    assertEquals("jane@example.com", dto.getEmail());
    assertEquals("87654321", dto.getPhoneNumber());
    assertEquals(Role.ROLE_USER, dto.getRole());
  }
}
