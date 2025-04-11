package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the User entity. It includes methods to find users by email and check if a user exists
 * by email or role.
 * <p>
 * This interface is used to interact with the database and perform operations related to users.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * Finds a user by their email address.
   *
   * @param email The email address of the user to find.
   * @return An Optional containing the found user, or empty if not found.
   */
  Optional<User> findByEmail(String email);

  /**
   * Checks if a user exists by their email address.
   *
   * @param email The email address to check.
   * @return true if a user with the given email exists, false otherwise.
   */
  boolean existsByEmail(String email);

  /**
   * Checks if a user exists by their role.
   *
   * @param role The role to check.
   * @return true if a user with the given role exists, false otherwise.
   */
  boolean existsByRole(Role role);
}
