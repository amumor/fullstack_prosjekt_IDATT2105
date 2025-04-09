package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Bookmark entity representing a user's saved listing.
 * Contains information about the user, listing, and the time the bookmark was created.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "listing_id"})
})
public class Bookmark {

  /**
   * Unique identifier for the bookmark.
   * Generated using UUID strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The user who created the bookmark.
   * This is a many-to-one relationship with the User entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  /**
   * The listing associated with the bookmark.
   * This is a many-to-one relationship with the Listing entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

  /**
   * The time when the bookmark was created.
   * This field is automatically set to the current time when the bookmark is created.
   */
  @Column(nullable = false)
  private LocalDateTime savedAt;

  /**
   * Automatically sets the savedAt field to the current time when the bookmark is created.
   * This method is called before the entity is persisted to the database.
   */
  @PrePersist
  protected void onCreate() {
    savedAt = LocalDateTime.now();
  }
}