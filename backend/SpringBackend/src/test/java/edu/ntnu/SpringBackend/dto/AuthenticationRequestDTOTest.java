package edu.ntnu.SpringBackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestDTOTest {

  @Test
  void testBuilderAndGetters() {
    AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
            .email("test@example.com")
            .password("Password@123")
            .build();

    assertEquals("test@example.com", dto.getEmail());
    assertEquals("Password@123", dto.getPassword());
  }

  @Test
  void testNoArgsConstructorAndSetters() {
    AuthenticationRequestDTO dto = new AuthenticationRequestDTO();
    dto.setEmail("new@example.com");
    dto.setPassword("NewPass@456");

    assertEquals("new@example.com", dto.getEmail());
    assertEquals("NewPass@456", dto.getPassword());
  }
}
