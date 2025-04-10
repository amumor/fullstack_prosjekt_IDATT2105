package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  private UserRequestDTO validDTO;
  private User existingUser;

  @BeforeEach
  void setUp() {
    validDTO = UserRequestDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("Password@123")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    existingUser = User.builder()
            .id(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("encodedPassword@123")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();
  }

  @Test
  void testGetAllUsers() {
    List<User> users = Collections.singletonList(existingUser);
    when(userRepository.findAll()).thenReturn(users);

    List<User> result = userService.getAllUsers();
    assertEquals(users, result);
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testGetUserByIdFound() {
    UUID userId = existingUser.getId();
    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

    User result = userService.getUserById(userId);
    assertEquals(existingUser, result);
    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void testGetUserByIdNotFound() {
    UUID userId = UUID.randomUUID();
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
            () -> userService.getUserById(userId));
    assertTrue(exception.getMessage().contains("User with ID " + userId + " not found"));
  }

  @Test
  void testGetUserByEmailFound() {
    when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(existingUser));
    User result = userService.getUserByEmail("john@example.com");
    assertEquals(existingUser, result);
    verify(userRepository, times(1)).findByEmail("john@example.com");
  }

  @Test
  void testGetUserByEmailNotFound() {
    when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
            () -> userService.getUserByEmail("nonexistent@example.com"));
    assertTrue(exception.getMessage().contains("User with email nonexistent@example.com not found"));
  }

  @Test
  void testAddUserSuccess() {
    when(passwordEncoder.encode(validDTO.getPassword())).thenReturn("encodedPassword@123");
    when(userRepository.existsByEmail(validDTO.getEmail())).thenReturn(false);
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User user = invocation.getArgument(0);
      user.setId(UUID.randomUUID());
      return user;
    });

    User result = userService.addUser(validDTO);

    assertNotNull(result.getId());
    assertEquals(validDTO.getFirstName(), result.getFirstName());
    assertEquals(validDTO.getLastName(), result.getLastName());
    assertEquals(validDTO.getEmail(), result.getEmail());
    assertEquals(validDTO.getPhoneNumber(), result.getPhoneNumber());
    assertEquals(validDTO.getRole(), result.getRole());
    assertEquals("encodedPassword@123", result.getPassword());
    verify(passwordEncoder, times(1)).encode(validDTO.getPassword());
  }

  @Test
  void testAddUserDuplicateEmail() {
    when(userRepository.existsByEmail(validDTO.getEmail())).thenReturn(true);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.addUser(validDTO));
    assertEquals("Email is already in use.", exception.getMessage());
  }

  @Test
  void testAddUserDuplicatePhone() {
    when(userRepository.existsByEmail(validDTO.getEmail())).thenReturn(false);
    // Simulate existing user with the same phone number.
    User otherUser = User.builder()
            .id(UUID.randomUUID())
            .firstName("Alice")
            .lastName("Smith")
            .email("alice@example.com")
            .phoneNumber(validDTO.getPhoneNumber())
            .password("encodedPassword")
            .role(Role.ROLE_USER)
            .build();
    when(userRepository.findAll()).thenReturn(Collections.singletonList(otherUser));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.addUser(validDTO));
    assertEquals("Phone number is already in use.", exception.getMessage());
  }

  @Test
  void testAddUserInvalidEmail() {
    UserRequestDTO dto = UserRequestDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("invalid-email")
            .password("Password@123")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.addUser(dto));
    assertEquals("Invalid email format.", exception.getMessage());
  }

  @Test
  void testAddUserInvalidPassword() {
    UserRequestDTO dto = UserRequestDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("weakpass")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.addUser(dto));
    assertTrue(exception.getMessage().contains("Password must be at least 10 characters"));
  }

  @Test
  void testAddUserInvalidFirstName() {
    UserRequestDTO dto = UserRequestDTO.builder()
            .firstName("John123")
            .lastName("Doe")
            .email("john@example.com")
            .password("Password@123")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.addUser(dto));
    assertEquals("Names must only contain letters.", exception.getMessage());
  }

  @Test
  void testAddUserInvalidPhone() {
    UserRequestDTO dto = UserRequestDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("Password@123")
            .phoneNumber("1234")
            .role(Role.ROLE_USER)
            .build();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.addUser(dto));
    assertEquals("Phone number must be exactly 8 digits.", exception.getMessage());
  }

  @Test
  void testUpdateUserSuccess() {
    // Simulate an authenticated user.
    User authUser = existingUser;
    // Create a DTO with updated information.
    UserRequestDTO dto = UserRequestDTO.builder()
            .email("newemail@example.com")
            .password("NewPassword@123")
            .firstName("Johnny")
            .lastName("Doey")
            .phoneNumber("87654321")
            .role(Role.ROLE_ADMIN)
            .build();

    when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(existingUser));
    when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedNewPassword@123");
    when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User updatedUser = userService.updateUser(authUser, dto);

    assertEquals("newemail@example.com", updatedUser.getEmail());
    assertEquals("encodedNewPassword@123", updatedUser.getPassword());
    assertEquals("Johnny", updatedUser.getFirstName());
    assertEquals("Doey", updatedUser.getLastName());
    assertEquals("87654321", updatedUser.getPhoneNumber());
    assertEquals(Role.ROLE_ADMIN, updatedUser.getRole());
  }

  @Test
  void testUpdateUserEmailDuplicate() {
    User authUser = existingUser;
    UserRequestDTO dto = UserRequestDTO.builder()
            .email("duplicate@example.com")
            .build();

    when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(existingUser));
    when(userRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.updateUser(authUser, dto));
    assertEquals("Email already in use.", exception.getMessage());
  }

  @Test
  void testUpdateUserPhoneDuplicate() {
    User authUser = existingUser;
    UserRequestDTO dto = UserRequestDTO.builder()
            .phoneNumber("99999999")
            .build();

    // Simulate an existing user with the conflicting phone number.
    User conflictingUser = User.builder()
            .id(UUID.randomUUID())
            .firstName("Alice")
            .lastName("Smith")
            .email("alice@example.com")
            .password("encoded")
            .phoneNumber("99999999")
            .role(Role.ROLE_USER)
            .build();

    when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(existingUser));
    when(userRepository.findAll()).thenReturn(Collections.singletonList(conflictingUser));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.updateUser(authUser, dto));
    assertEquals("Phone number already in use.", exception.getMessage());
  }

  @Test
  void testUpdateUserNotFound() {
    User authUser = existingUser;
    UserRequestDTO dto = UserRequestDTO.builder().build();
    when(userRepository.findById(authUser.getId())).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
            () -> userService.updateUser(authUser, dto));
    assertTrue(exception.getMessage().contains("User with ID " + authUser.getId() + " not found"));
  }

  @Test
  void testDeleteUserByIdSuccess() {
    UUID userId = existingUser.getId();
    when(userRepository.existsById(userId)).thenReturn(true);
    doNothing().when(userRepository).deleteById(userId);

    assertDoesNotThrow(() -> userService.deleteUserById(userId));
    verify(userRepository, times(1)).deleteById(userId);
  }

  @Test
  void testDeleteUserByIdNotFound() {
    UUID userId = UUID.randomUUID();
    when(userRepository.existsById(userId)).thenReturn(false);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
            () -> userService.deleteUserById(userId));
    assertTrue(exception.getMessage().contains("User with ID " + userId + " does not exist"));
  }

  @Test
  void testVerifyPassword() {
      String rawPassword = "Password@123";
    String encodedPassword = "encodedPassword@123";
    User user = User.builder()
            .password(encodedPassword)
            .build();

    when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

    boolean result = userService.verifyPassword(user, rawPassword);

    assertTrue(result);
    verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
  }

  @Test
  void testValidateEmail() {
    // Valid email should not throw.
    assertDoesNotThrow(() -> userService.validateEmail("test@example.com"));
    // An invalid email should throw an exception.
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.validateEmail("invalid-email"));
    assertEquals("Invalid email format.", exception.getMessage());
  }

  @Test
  void testValidatePassword() {
    // Valid password test.
    assertDoesNotThrow(() -> userService.validatePassword("ValidPassword@1"));
    // Invalid password test.
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.validatePassword("weakpass"));
    assertTrue(exception.getMessage().contains("Password must be at least 10 characters"));
  }

  @Test
  void testValidateName() {
    // Valid name.
    assertDoesNotThrow(() -> userService.validateName("John"));
    // Invalid name (contains digits).
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.validateName("John123"));
    assertEquals("Names must only contain letters.", exception.getMessage());
  }

  @Test
  void testValidatePhone() {
    // Valid phone.
    assertDoesNotThrow(() -> userService.validatePhone("12345678"));
    // Invalid phone.
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.validatePhone("1234"));
    assertEquals("Phone number must be exactly 8 digits.", exception.getMessage());
  }
}
