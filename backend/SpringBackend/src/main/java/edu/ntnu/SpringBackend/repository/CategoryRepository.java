package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Category entities.
 * This interface extends JpaRepository to provide CRUD operations for Category entities.
 * It also includes custom query methods for finding categories by name.
 * <p>
 * JPA (Java Persistence API) is a specification for accessing, persisting,
 * and managing data between Java objects and relational databases.
 * It provides a standard way to map Java objects to database tables and vice versa.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  /**
   * Finds a category by its name, ignoring case.
   *
   * @param name The name of the category to find.
   * @return An Optional containing the found category, or empty if not found.
   */
  Optional<Category> findByNameIgnoreCase(String name);

  /**
   * Checks if a category with the given name exists, ignoring the case.
   *
   * @param name The name of the category to check.
   * @return true if a category with the given name exists, false otherwise.
   */
  boolean existsByNameIgnoreCase(String name);
}
