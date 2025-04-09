package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * SearchHistory entity representing a user's search history.
 * Contains information about the user, search query, and the time the search was made.
 *
 * @author Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "searchHistories")
public class SearchHistory {

  /**
   * Unique identifier for the search history entry.
   * Generated using UUID strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The user who made the search.
   * This is a many-to-one relationship with the User entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  /**
   * The search query made by the user.
   * This field cannot be null and is stored as TEXT in the database.
   */
  @Column(nullable = false)
  private String searchQuery;

  /**
   * The time when the search was made.
   * This field is automatically set to the current time when the search history entry is created.
   */
  @Column(nullable = false)
  private LocalDateTime searchedAt;
}
