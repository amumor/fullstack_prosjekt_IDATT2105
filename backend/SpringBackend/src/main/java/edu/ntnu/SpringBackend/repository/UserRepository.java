package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  boolean existsByRole(Role role);
}
