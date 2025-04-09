package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.AuthenticationRequestDTO;
import edu.ntnu.SpringBackend.dto.TokenResponseDTO;
import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AuthenticationServiceTest {

  @Mock
  private UserRepository repository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private UserService userService;

  @InjectMocks
  private AuthenticationService authenticationService;

  private UserRequestDTO validUserRequest;
  private AuthenticationRequestDTO validAuthRequest;
  private User user;

  @BeforeEach
  void setUp() {
    // Prepare valid registration request
    validUserRequest = UserRequestDTO.builder()
            .email("test@example.com")
            .password("Password@123")
            .firstName("John")
            .lastName("Doe")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    // Prepare valid authentication request
    validAuthRequest = AuthenticationRequestDTO.builder()
            .email("test@example.com")
            .password("Password@123")
            .build();

    // Build a sample user object
    user = User.builder()
            .email("test@example.com")
            .firstName("John")
            .lastName("Doe")
            .password("encodedPassword")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();
  }

  @Test
  void testRegisterSuccess() {
    // Simulate that no user exists with this email.
    when(repository.findByEmail(validUserRequest.getEmail())).thenReturn(Optional.empty());
    // Assume that validations pass without exceptions.
    doNothing().when(userService).validateName(validUserRequest.getFirstName());
    doNothing().when(userService).validateName(validUserRequest.getLastName());
    doNothing().when(userService).validatePhone(validUserRequest.getPhoneNumber());
    doNothing().when(userService).validatePassword(validUserRequest.getPassword());
    doNothing().when(userService).validateEmail(validUserRequest.getEmail());

    // Simulate encoding of the password.
    when(passwordEncoder.encode(validUserRequest.getPassword())).thenReturn("encodedPassword");
    // Save will simply return the user that is passed.
    when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
    // Simulate JWT token generation.
    when(jwtService.generateToken(any(User.class))).thenReturn("dummyToken");

    TokenResponseDTO response = authenticationService.register(validUserRequest);

    assertNotNull(response);
    assertEquals("dummyToken", response.getToken());

    verify(repository).findByEmail(validUserRequest.getEmail());
    verify(userService).validateName(validUserRequest.getFirstName());
    verify(userService).validateName(validUserRequest.getLastName());
    verify(userService).validatePhone(validUserRequest.getPhoneNumber());
    verify(userService).validatePassword(validUserRequest.getPassword());
    verify(userService).validateEmail(validUserRequest.getEmail());
    verify(passwordEncoder).encode(validUserRequest.getPassword());
    verify(repository).save(any(User.class));
    verify(jwtService).generateToken(any(User.class));
  }

  @Test
  void testRegisterEmailAlreadyExists() {
    // Simulate that the user is already in the repository.
    when(repository.findByEmail(validUserRequest.getEmail())).thenReturn(Optional.of(user));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> authenticationService.register(validUserRequest));
    assertEquals("User with email " + validUserRequest.getEmail() + " already exists.", exception.getMessage());

    verify(repository).findByEmail(validUserRequest.getEmail());
    verifyNoMoreInteractions(passwordEncoder, jwtService, userService);
  }

  @Test
  void testAuthenticateSuccess() {
    // Simulate successful authentication.
    doAnswer(invocation -> null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    // Simulate finding the user by email.
    when(repository.findByEmail(validAuthRequest.getEmail())).thenReturn(Optional.of(user));

    // Simulate token generation.
    when(jwtService.generateToken(user)).thenReturn("dummyToken");

    TokenResponseDTO response = authenticationService.authenticate(validAuthRequest);

    assertNotNull(response);
    assertEquals("dummyToken", response.getToken());

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(repository).findByEmail(validAuthRequest.getEmail());
    verify(jwtService).generateToken(user);
  }

  @Test
  void testAuthenticateAuthenticationFailure() {
    // Simulate authentication failure by making the authentication manager throw an exception.
    AuthenticationException authEx = new AuthenticationException("Auth failed") {};
    doThrow(authEx).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    AuthenticationException thrown = assertThrows(AuthenticationException.class,
            () -> authenticationService.authenticate(validAuthRequest));
    assertEquals("Auth failed", thrown.getMessage());

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(repository, never()).findByEmail(anyString());
    verify(jwtService, never()).generateToken(any(User.class));
  }

  @Test
  void testAuthenticateUserNotFound() {
    // Simulate successful authentication by not throwing an exception.
    doAnswer(invocation -> null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    // Simulate no user found in the repository.
    when(repository.findByEmail(validAuthRequest.getEmail())).thenReturn(Optional.empty());

    // Assert that a NoSuchElementException is thrown with the correct message.
    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
            () -> authenticationService.authenticate(validAuthRequest));
    assertEquals("User not found with email: " + validAuthRequest.getEmail(), exception.getMessage());

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(repository).findByEmail(validAuthRequest.getEmail());
    verify(jwtService, never()).generateToken(any(User.class));
  }
}
