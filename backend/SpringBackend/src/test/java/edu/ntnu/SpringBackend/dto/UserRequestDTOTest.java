package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRequestDTOTest {

  @Test
  void testBuilderAndGetters() {
    UserRequestDTO dto = UserRequestDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("Password@123")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    assertEquals("John", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
    assertEquals("john@example.com", dto.getEmail());
    assertEquals("Password@123", dto.getPassword());
    assertEquals("12345678", dto.getPhoneNumber());
    assertEquals(Role.ROLE_USER, dto.getRole());
  }

  @Test
  void testNoArgsConstructorAndSetters() {
    UserRequestDTO dto = new UserRequestDTO();
    dto.setFirstName("Jane");
    dto.setLastName("Doe");
    dto.setEmail("jane@example.com");
    dto.setPassword("Password@123");
    dto.setPhoneNumber("87654321");
    dto.setRole(Role.ROLE_USER);  // Assumes ADMIN exists in your Role enum

    assertEquals("Jane", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
    assertEquals("jane@example.com", dto.getEmail());
    assertEquals("Password@123", dto.getPassword());
    assertEquals("87654321", dto.getPhoneNumber());
    assertEquals(Role.ROLE_USER, dto.getRole());
  }
}
