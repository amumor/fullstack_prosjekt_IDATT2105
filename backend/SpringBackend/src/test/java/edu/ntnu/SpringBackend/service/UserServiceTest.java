package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {

  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;

  private User testUser;

  @BeforeEach
  void setup() {
    testUser = User.builder()
            .email("user@example.com")
            .password("testpassword")
            .firstName("Test")
            .lastName("User")
            .phoneNumber("12345678")
            .role(Role.USER)
            .build();

    testUser = userService.addUser(testUser);
  }

  @Test
  void addUser_Success() {
    User newUser = User.builder()
            .email("newuser@example.com")
            .password("newpassword")
            .firstName("New")
            .lastName("User")
            .phoneNumber("87654321")
            .role(Role.USER)
            .build();

    User createdUser = userService.addUser(newUser);

    assertThat(createdUser.getId()).isGreaterThan(0);
    assertThat(createdUser.getEmail()).isEqualTo("newuser@example.com");
    assertThat(passwordEncoder.matches("newpassword", createdUser.getPassword())).isTrue();
  }

  @Test
  void getUserById_Success() {
    Optional<User> foundUser = userService.getUserById(testUser.getId());

    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getEmail()).isEqualTo(testUser.getEmail());
  }

  @Test
  void getUserByEmail_Success() {
    Optional<User> foundUser = userService.getUserByEmail("user@example.com");

    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getFirstName()).isEqualTo("Test");
  }

  @Test
  void updateUser_Success() {
    User updatedDetails = User.builder()
            .firstName("Updated")
            .lastName("New")
            .build();

    Optional<User> updatedUserOpt = userService.updateUser(testUser.getId(), updatedDetails);
    assertThat(updatedUserOpt).isPresent();

    User updatedUser = updatedUserOpt.get();
    assertThat(updatedUser.getFirstName()).isEqualTo("Updated");
    assertThat(updatedUser.getLastName()).isEqualTo("New");
    assertThat(updatedUser.getLastName()).isNotEqualTo("User");
  }

  @Test
  void deleteUserById_Success() {
    userService.deleteUserById(testUser.getId());

    Optional<User> deletedUser = userService.getUserById(testUser.getId());
    assertThat(deletedUser).isEmpty();
  }

  @Test
  void verifyPassword_Success() {
    boolean isCorrect = userService.verifyPassword(testUser, "testpassword");
    boolean isIncorrect = userService.verifyPassword(testUser, "wrongpassword");

    assertThat(isCorrect).isTrue();
    assertThat(isIncorrect).isFalse();
  }

  @Test
  void getAllUsers_Success() {
    List<User> allUsers = userService.getAllUsers();
    assertThat(allUsers).isNotEmpty();
    assertThat(allUsers).hasSizeGreaterThanOrEqualTo(1);
  }
}
