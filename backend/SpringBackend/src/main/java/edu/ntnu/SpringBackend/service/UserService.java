package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  public List<User> getAllUsers() {
    logger.info("Fetching all users...");
    return userRepository.findAll();
  }

  public User getUserById(UUID id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("User with email " + email + " not found"));
  }

  @Transactional
  public User addUser(User user) {
    logger.info("Adding user: {}", user.getEmail());

    validateEmail(user.getEmail());
    validatePassword(user.getPassword());
    validateName(user.getFirstName());
    validateName(user.getLastName());
    validatePhone(user.getPhoneNumber());

    if (userRepository.existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    if (userRepository.findAll().stream().anyMatch(u -> u.getPhoneNumber().equals(user.getPhoneNumber()))) {
      throw new IllegalArgumentException("Phone number is already in use.");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }


  @Transactional
  public User updateUser(UUID id, User updatedUser) {
    User existingUser = getUserById(id);

    // Email
    if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
      validateEmail(updatedUser.getEmail());
      if (userRepository.existsByEmail(updatedUser.getEmail())) {
        throw new IllegalArgumentException("Email already in use.");
      }
      existingUser.setEmail(updatedUser.getEmail());
    }

    // Password
    if (updatedUser.getPassword() != null) {
      validatePassword(updatedUser.getPassword());
      existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    }

    // First name
    if (updatedUser.getFirstName() != null) {
      validateName(updatedUser.getFirstName());
      existingUser.setFirstName(updatedUser.getFirstName());
    }

    // Last name
    if (updatedUser.getLastName() != null) {
      validateName(updatedUser.getLastName());
      existingUser.setLastName(updatedUser.getLastName());
    }

    // Phone number
    if (updatedUser.getPhoneNumber() != null && !updatedUser.getPhoneNumber().equals(existingUser.getPhoneNumber())) {
      validatePhone(updatedUser.getPhoneNumber());

      if (userRepository.findAll().stream().anyMatch(u -> u.getPhoneNumber().equals(updatedUser.getPhoneNumber()))) {
        throw new IllegalArgumentException("Phone number already in use.");
      }

      existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
    }

    // Role
    if (updatedUser.getRole() != null) {
      existingUser.setRole(updatedUser.getRole());
    }

    return userRepository.save(existingUser);
  }


  @Transactional
  public void deleteUserById(UUID id) {
    logger.info("Deleting user with ID: {}", id);
    if (!userRepository.existsById(id)) {
      throw new NoSuchElementException("User with ID " + id + " does not exist");
    }
    userRepository.deleteById(id);
  }

  public boolean verifyPassword(User user, String password) {
    return passwordEncoder.matches(password, user.getPassword());
  }

  public void validateEmail(String email) {
    if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
      throw new IllegalArgumentException("Invalid email format.");
    }
  }

  public void validatePassword(String password) {
    if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"'|<>,.?]).{10,}$")) {
      throw new IllegalArgumentException("Password must be at least 10 characters, contain an uppercase letter, a digit, and a special character.");
    }
  }

  public void validateName(String name) {
    if (!name.matches("^[A-Za-zÆØÅæøå]+$")) {
      throw new IllegalArgumentException("Names must only contain letters.");
    }
  }

  public void validatePhone(String phone) {
    if (!phone.matches("^\\d{8}$")) {
      throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
    }
  }

}
