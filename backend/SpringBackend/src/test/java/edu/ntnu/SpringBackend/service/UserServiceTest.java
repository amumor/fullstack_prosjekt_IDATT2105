package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  @Test
  void testAddUserReturnsCorrectDTO() {
    UserRequestDTO request = UserRequestDTO.builder()
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@doe.com")
            .password("StrongPass123!")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    UUID id = UUID.randomUUID();
    User savedUser = User.builder()
            .id(id)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@doe.com")
            .password("encodedPass")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPass");
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.findAll()).thenReturn(List.of());
    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    User response = userService.addUser(request);

    assertEquals(id, response.getId());
    assertEquals("Jane", response.getFirstName());
    assertEquals("Doe", response.getLastName());
    assertEquals("jane@doe.com", response.getEmail());
    assertEquals("12345678", response.getPhoneNumber());
    assertEquals("USER", response.getRole().toString());
  }

  @Test
  void testGetAllUsersReturnsListOfDTOs() {
    User user = User.builder()
            .id(UUID.randomUUID())
            .firstName("John")
            .lastName("Smith")
            .email("john@smith.com")
            .password("secret")
            .phoneNumber("87654321")
            .role(Role.ROLE_ADMIN)
            .build();

    when(userRepository.findAll()).thenReturn(List.of(user));

    List<User> result = userService.getAllUsers();

    assertEquals(1, result.size());
    assertEquals("John", result.getFirst().getFirstName());
    assertEquals("Smith", result.getFirst().getLastName());
    assertEquals("john@smith.com", result.getFirst().getEmail());
    assertEquals("87654321", result.getFirst().getPhoneNumber());
    assertEquals("ROLE_ADMIN", result.getFirst().getRole().toString());
  }

  @Test
  void testGetUserByIdReturnsCorrectDTO() {
    UUID id = UUID.randomUUID();
    User user = User.builder()
            .id(id)
            .firstName("Alice")
            .lastName("Wonder")
            .email("alice@wonder.com")
            .password("pass")
            .phoneNumber("11112222")
            .role(Role.ROLE_USER)
            .build();

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    User result = userService.getUserById(id);

    assertEquals(id, result.getId());
    assertEquals("Alice", result.getFirstName());
    assertEquals("Wonder", result.getLastName());
    assertEquals("alice@wonder.com", result.getEmail());
    assertEquals("11112222", result.getPhoneNumber());
    assertEquals("USER", result.getRole().toString());
  }
}
