package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(int id) {
    return userRepository.findById(id);
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public User addUser(User user) {
    if (user.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    return userRepository.save(user);
  }

  @Transactional
  public Optional<User> updateUser(int id, User updatedUser) {
    return userRepository.findById(id)
            .map(existingUser -> {
              if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
              }
              if (updatedUser.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
              }
              if (updatedUser.getFirstName() != null) {
                existingUser.setFirstName(updatedUser.getFirstName());
              }
              if (updatedUser.getLastName() != null) {
                existingUser.setLastName(updatedUser.getLastName());
              }
              if (updatedUser.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
              }
              return userRepository.save(existingUser);
    });
  }

  @Transactional
  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  public boolean verifyPassword(User user, String password) {
    return passwordEncoder.matches(password, user.getPassword());
  }
}
