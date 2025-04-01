package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
            .password("TestPassword1!")
            .firstName("Test")
            .lastName("User")
            .phoneNumber("12345678")
            .role(Role.USER)
            .build();

    testUser = userService.addUser(testUser);
  }

  @Nested
  class UserValidationTests {

    @Test
    void shouldAcceptValidUser() {
      assertThat(testUser).isNotNull();
      assertThat(testUser.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    void shouldRejectInvalidEmail() {
      User user = testUser.toBuilder().email("not-an-email").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Invalid email format");
    }

    @Test
    void shouldRejectWeakPassword() {
      User user = testUser.toBuilder().password("weakpass").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Password must be at least");
    }

    @Test
    void shouldRejectInvalidFirstName() {
      User user = testUser.toBuilder().firstName("123John").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Names must only contain letters");
    }

    @Test
    void shouldRejectInvalidLastName() {
      User user = testUser.toBuilder().lastName("Doe!").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Names must only contain letters");
    }

    @Test
    void shouldRejectInvalidPhoneNumber() {
      User user = testUser.toBuilder().phoneNumber("1234abc").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Phone number must be exactly 8 digits");
    }

    @Test
    void shouldRejectTooShortPhoneNumber() {
      User user = testUser.toBuilder().phoneNumber("123").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Phone number must be exactly 8 digits");
    }

    @Test
    void shouldRejectPhoneNumberWithSpaces() {
      User user = testUser.toBuilder().phoneNumber("1234 567").build();
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Phone number must be exactly 8 digits");
    }
  }

  @Test
  void addUser_Success() {
    User newUser = User.builder()
            .email("newuser@example.com")
            .password("StrongPass1!")
            .firstName("New")
            .lastName("User")
            .phoneNumber("87654321")
            .role(Role.USER)
            .build();

    User createdUser = userService.addUser(newUser);

    assertThat(createdUser.getId()).isGreaterThan(0);
    assertThat(passwordEncoder.matches("StrongPass1!", createdUser.getPassword())).isTrue();
  }

  @Test
  void addUser_FailsWhenEmailExists() {
    User duplicate = testUser.toBuilder()
            .email("user@example.com")
            .phoneNumber("99999999") // different phone
            .build();

    assertThatThrownBy(() -> userService.addUser(duplicate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email is already in use");
  }

  @Test
  void addUser_FailsWhenEmailInvalid() {
    User user = testUser.toBuilder()
            .email("bademail")
            .phoneNumber("11111111")
            .build();

    assertThatThrownBy(() -> userService.addUser(user))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid email format");
  }

  @Test
  void addUser_FailsWhenPasswordWeak() {
    User user = testUser.toBuilder()
            .email("weakpass@example.com")
            .password("abc") // too weak
            .phoneNumber("22222222")
            .build();

    assertThatThrownBy(() -> userService.addUser(user))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Password must be at least");
  }

  @Test
  void addUser_FailsWhenPhoneInvalid() {
    User user = testUser.toBuilder()
            .email("phonefail@example.com")
            .phoneNumber("12345") // invalid
            .build();

    assertThatThrownBy(() -> userService.addUser(user))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Phone number must be exactly 8 digits");
  }

  @Test
  void getUserById_Success() {
    User found = userService.getUserById(testUser.getId());
    assertThat(found).isNotNull().hasFieldOrPropertyWithValue("email", testUser.getEmail());
  }

  @Test
  void getUserById_NotFound() {
    assertThatThrownBy(() -> userService.getUserById(99999))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void updateUser_Success() {
    User update = User.builder()
            .firstName("Updated")
            .lastName("Newname")
            .build();

    User updated = userService.updateUser(testUser.getId(), update);
    assertThat(updated.getFirstName()).isEqualTo("Updated");
    assertThat(updated.getLastName()).isEqualTo("Newname");
  }

  @Test
  void updateUser_FailsWhenEmailTaken() {
    User otherUser = User.builder()
            .email("other@example.com")
            .password("Password1!")
            .firstName("Other")
            .lastName("User")
            .phoneNumber("87654321")
            .role(Role.USER)
            .build();

    userService.addUser(otherUser);

    User update = User.builder().email("other@example.com").build();

    assertThatThrownBy(() -> userService.updateUser(testUser.getId(), update))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email already in use");
  }

  @Test
  void deleteUserById_Success() {
    userService.deleteUserById(testUser.getId());

    assertThatThrownBy(() -> userService.getUserById(testUser.getId()))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void deleteUserById_NotFound() {
    assertThatThrownBy(() -> userService.deleteUserById(99999))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void verifyPassword_Success() {
    assertThat(userService.verifyPassword(testUser, "TestPassword1!")).isTrue();
    assertThat(userService.verifyPassword(testUser, "WrongPassword")).isFalse();
  }

  @Test
  void getAllUsers_Success() {
    List<User> users = userService.getAllUsers();
    assertThat(users).isNotEmpty();
  }

  @Test
  void getUserByEmail_Success() {
    User found = userService.getUserByEmail(testUser.getEmail());
    assertThat(found).isNotNull().hasFieldOrPropertyWithValue("email", testUser.getEmail());
  }

  @Test
  void getUserByEmail_NotFound() {
    assertThatThrownBy(() -> userService.getUserByEmail("noonehastilmail@example.com"))
            .isInstanceOf(NoSuchElementException.class);
  }
}
