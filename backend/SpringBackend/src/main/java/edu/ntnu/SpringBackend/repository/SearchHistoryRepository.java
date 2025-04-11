package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing SearchHistory entities.
 * This interface extends JpaRepository to provide CRUD operations for SearchHistory entities.
 * It also includes custom query methods for finding search history by user ID and deleting old searches.
 * <p>
 * JPA (Java Persistence API) is a specification for accessing, persisting,
 * and managing data between Java objects and relational databases.
 * It provides a standard way to map Java objects to database tables and vice versa.
 *
 * @author Amund Mørk
 * @version 1.0
 * @since 1.0
 */
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, UUID> {

  /**
   * Finds all search history entries for a specific user, ordered by the date they were searched.
   *
   * @param userId The ID of the user whose search history is to be fetched.
   * @return A list of search history entries associated with the specified user, ordered by searched date.
   */
  List<SearchHistory> findByUserIdOrderBySearchedAtDesc(UUID userId);

  /**
   * Deletes old search history entries for a specific user, keeping only the most recent 15 entries.
   *
   * @param userId The ID of the user whose old search history is to be deleted.
   */
  @Modifying
  @Query(value = """
          DELETE FROM search_history
          WHERE id IN (
              SELECT id FROM (
                  SELECT id,
                         ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY searched_at DESC) AS rn
                  FROM search_history
                  WHERE user_id = :userId
              ) sub
              WHERE rn > 15
          )
          """, nativeQuery = true)
  void deleteOldSearches(@Param("userId") Long userId);

  /**
   * Finds all search history entries for a specific user.
   *
   * @param user The user whose search history is to be fetched.
   * @return A list of search history entries associated with the specified user.
   */
  UUID user(User user);
}
