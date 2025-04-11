package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Service class for managing users.
 * This class provides methods to create, update, delete, and retrieve users.
 * It also includes validation methods for user data such as email, password, name, and phone number.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  /**
   * Fetches all users from the database.
   *
   * @return A list of all users.
   */
  public List<User> getAllUsers() {
    logger.info("> Fetching all users");
    return userRepository.findAll();
  }

  /**
   * Fetches a user by their ID.
   *
   * @param id The ID of the user to fetch.
   * @return The user with the specified ID.
   */
  public User getUserById(UUID id) {
    logger.info("> Fetching user by id: {}", id);
    return userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));
  }

  /**
   * Fetches a user by their email.
   *
   * @param email The email of the user to fetch.
   * @return The user with the specified email.
   */
  public User getUserByEmail(String email) {
    logger.info("> Fetching user by email: {}", email);
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("User with email " + email + " not found"));
  }

  /**
   * Adds a new user to the database.
   * Validates the user data and checks for existing email and phone number.
   *
   * @param userDTO The user data transfer object containing user information.
   * @return The created user.
   */
  @Transactional
  public User addUser(UserRequestDTO userDTO) {
    logger.info("> Adding user: {}", userDTO.getEmail());

    validateEmail(userDTO.getEmail());
    validatePassword(userDTO.getPassword());
    validateName(userDTO.getFirstName());
    validateName(userDTO.getLastName());
    validatePhone(userDTO.getPhoneNumber());

    if (userRepository.existsByEmail(userDTO.getEmail())) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    if (userRepository.findAll().stream().anyMatch(u -> u.getPhoneNumber().equals(userDTO.getPhoneNumber()))) {
      throw new IllegalArgumentException("Phone number is already in use.");
    }

    User user = User.builder()
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .email(userDTO.getEmail())
            .phoneNumber(userDTO.getPhoneNumber())
            .password(passwordEncoder.encode(userDTO.getPassword()))
            .role(userDTO.getRole())
            .build();

    return userRepository.save(user);
  }

  /**
   * Updates an existing user in the database.
   * Validates the user data and checks for existing email and phone number.
   *
   * @param userDTO The user data transfer object containing updated user information.
   * @return The updated user.
   */
  @Transactional
  public User updateUser(@AuthenticationPrincipal User user, UserRequestDTO userDTO) {
    logger.info("> Updating user with ID: {}", user.getId().toString());
    User existingUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NoSuchElementException("User with ID " + user.getId() + " not found"));

    if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
      validateEmail(userDTO.getEmail());
      if (userRepository.existsByEmail(userDTO.getEmail())) {
        throw new IllegalArgumentException("Email already in use.");
      }
      existingUser.setEmail(userDTO.getEmail());
    }

    if (userDTO.getPassword() != null) {
      validatePassword(userDTO.getPassword());
      existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    if (userDTO.getFirstName() != null) {
      validateName(userDTO.getFirstName());
      existingUser.setFirstName(userDTO.getFirstName());
    }

    if (userDTO.getLastName() != null) {
      validateName(userDTO.getLastName());
      existingUser.setLastName(userDTO.getLastName());
    }

    if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().equals(existingUser.getPhoneNumber())) {
      validatePhone(userDTO.getPhoneNumber());
      if (userRepository.findAll().stream().anyMatch(u -> u.getPhoneNumber().equals(userDTO.getPhoneNumber()))) {
        throw new IllegalArgumentException("Phone number already in use.");
      }
      existingUser.setPhoneNumber(userDTO.getPhoneNumber());
    }

    if (userDTO.getRole() != null) {
      existingUser.setRole(userDTO.getRole());
    }

    logger.info("> User updated successfully");
    return userRepository.save(existingUser);
  }

  /**
   * Deletes a user by their ID.
   *
   * @param id The ID of the user to delete.
   */
  @Transactional
  public void deleteUserById(UUID id) {
    logger.info("> Deleting user with ID: {}", id);
    if (!userRepository.existsById(id)) {
      throw new NoSuchElementException("User with ID " + id + " does not exist");
    }
    userRepository.deleteById(id);
    logger.info("> User deleted successfully");
  }

  /**
   * Verifies if the provided password matches the stored password for the user.
   *
   * @param user     The user whose password is to be verified.
   * @param password The password to verify.
   * @return True if the password matches, false otherwise.
   */
  public boolean verifyPassword(User user, String password) {
    return passwordEncoder.matches(password, user.getPassword());
  }

  /**
   * Validates the email format.
   * Must be a valid email address.
   *
   * @param email The email to validate.
   * @throws IllegalArgumentException if the email format is invalid.
   */
  public void validateEmail(String email) {
    if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
      throw new IllegalArgumentException("Invalid email format.");
    }
  }

  /**
   * Validates the password format.
   * Must be at least 10 characters long,
   * contain at least one uppercase letter,
   * one digit,
   * and one special character.
   *
   * @param password The password to validate.
   * @throws IllegalArgumentException if the password format is invalid.
   */
  public void validatePassword(String password) {
    if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"'|<>,.?]).{10,}$")) {
      throw new IllegalArgumentException("Password must be at least 10 characters, contain an uppercase letter, a digit, and a special character.");
    }
  }

  /**
   * Validates the name format.
   * Must only contain letters (including Norwegian letters Æ, Ø, Å).
   *
   * @param name The name to validate.
   * @throws IllegalArgumentException if the name format is invalid.
   */
  public void validateName(String name) {
    if (!name.matches("^[A-Za-zÆØÅæøå]+$")) {
      throw new IllegalArgumentException("Names must only contain letters.");
    }
  }

  /**
   * Validates the phone number format.
   * Must be exactly 8 digits long.
   *
   * @param phone The phone number to validate.
   * @throws IllegalArgumentException if the phone number format is invalid.
   */
  public void validatePhone(String phone) {
    if (!phone.matches("^\\d{8}$")) {
      throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
    }
  }

}
