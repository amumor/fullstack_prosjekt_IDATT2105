package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.mapper.UserMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  public List<UserResponseDTO> getAllUsers() {
    logger.info("Fetching all users...");
    return userRepository.findAll().stream()
            .map(UserMapper::toDto)
            .collect(Collectors.toList());
  }

  public UserResponseDTO getUserById(UUID id) {
    return userRepository.findById(id)
            .map(UserMapper::toDto)
            .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));
  }

  public UserResponseDTO getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .map(UserMapper::toDto)
            .orElseThrow(() -> new NoSuchElementException("User with email " + email + " not found"));
  }

  @Transactional
  public UserResponseDTO addUser(UserRequestDTO userDTO) {
    logger.info("Adding user: {}", userDTO.getEmail());

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

    User user = UserMapper.toEntity(userDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return UserMapper.toDto(userRepository.save(user));
  }

  @Transactional
  public UserResponseDTO updateUser(UUID id, UserRequestDTO userDTO) {
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

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

    return UserMapper.toDto(userRepository.save(existingUser));
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
