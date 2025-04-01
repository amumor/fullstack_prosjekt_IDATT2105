package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
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
            .role(Role.USER)
            .build();

    User savedUser = User.builder()
            .id(1)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@doe.com")
            .password("encodedPass")
            .phoneNumber("12345678")
            .role(Role.USER)
            .build();

    when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPass");
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.findAll()).thenReturn(List.of());
    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    UserResponseDTO response = userService.addUser(request);

    assertEquals("1", response.getId());
    assertEquals("Jane", response.getFirstName());
    assertEquals("Doe", response.getLastName());
    assertEquals("jane@doe.com", response.getEmail());
    assertEquals("12345678", response.getPhoneNumber());
    assertEquals("USER", response.getRole());
  }

  @Test
  void testGetAllUsersReturnsListOfDTOs() {
    User user = User.builder()
            .id(1)
            .firstName("John")
            .lastName("Smith")
            .email("john@smith.com")
            .password("secret")
            .phoneNumber("87654321")
            .role(Role.ADMIN)
            .build();

    when(userRepository.findAll()).thenReturn(List.of(user));

    List<UserResponseDTO> result = userService.getAllUsers();

    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getFirstName());
    assertEquals("Smith", result.get(0).getLastName());
    assertEquals("john@smith.com", result.get(0).getEmail());
    assertEquals("87654321", result.get(0).getPhoneNumber());
    assertEquals("ADMIN", result.get(0).getRole());
  }

  @Test
  void testGetUserByIdReturnsCorrectDTO() {
    User user = User.builder()
            .id(42)
            .firstName("Alice")
            .lastName("Wonder")
            .email("alice@wonder.com")
            .password("pass")
            .phoneNumber("11112222")
            .role(Role.USER)
            .build();

    when(userRepository.findById(42)).thenReturn(Optional.of(user));

    UserResponseDTO result = userService.getUserById(42);

    assertEquals("42", result.getId());
    assertEquals("Alice", result.getFirstName());
    assertEquals("Wonder", result.getLastName());
    assertEquals("alice@wonder.com", result.getEmail());
    assertEquals("11112222", result.getPhoneNumber());
    assertEquals("USER", result.getRole());
  }
}
