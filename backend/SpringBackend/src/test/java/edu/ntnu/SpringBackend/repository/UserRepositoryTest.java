package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  private User createUser(String email, String phoneNumber) {
    return User.builder()
            .firstName("Alice")
            .lastName("Smith")
            .email(email)
            .phoneNumber(phoneNumber)
            .password("Password@123")
            .role(Role.ROLE_USER)
            .build();
  }

  @Test
  void testFindByEmail() {
    User user = createUser("alice@example.com", "12345678");
    userRepository.save(user);

    Optional<User> found = userRepository.findByEmail("alice@example.com");
    assertTrue(found.isPresent());
    assertEquals("alice@example.com", found.get().getEmail());
  }

  @Test
  void testExistsByEmail() {
    User user = createUser("bob@example.com", "87654321");
    userRepository.save(user);
    assertTrue(userRepository.existsByEmail("bob@example.com"));
  }

  @Test
  void testExistsByRole() {
    User user = createUser("charlie@example.com", "11223344");
    userRepository.save(user);
    assertTrue(userRepository.existsByRole(Role.ROLE_USER));
  }
}
