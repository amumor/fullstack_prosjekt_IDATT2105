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
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  boolean existsByRole(Role role);
}
