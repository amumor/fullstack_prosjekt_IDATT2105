package edu.ntnu.SpringBackend.model;

import edu.ntnu.SpringBackend.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @Test
  void testUserDetailsMethods() {
    User user = User.builder()
            .id(java.util.UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("secret")
            .phoneNumber("12345678")
            .role(Role.ROLE_USER)
            .build();

    // getUsername() should return the email.
    assertEquals("john@example.com", user.getUsername());
    assertEquals("secret", user.getPassword());

    // Test authorities – should contain a single authority equal to the role name.
    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    assertNotNull(authorities);
    assertEquals(1, authorities.size());
    GrantedAuthority authority = authorities.iterator().next();
    assertEquals(Role.ROLE_USER.name(), authority.getAuthority());

    // The rest of UserDetails methods should return true.
    assertTrue(user.isAccountNonExpired());
    assertTrue(user.isAccountNonLocked());
    assertTrue(user.isCredentialsNonExpired());
    assertTrue(user.isEnabled());

    // Check that the toString method contains key information.
    String userString = user.toString();
    assertTrue(userString.contains("john@example.com"));
    assertTrue(userString.contains("John"));
    assertTrue(userString.contains("Doe"));
    assertTrue(userString.contains(Role.ROLE_USER.toString()));
  }
}
