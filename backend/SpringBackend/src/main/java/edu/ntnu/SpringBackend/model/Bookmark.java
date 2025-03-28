package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "listing_id"})
})
public class Bookmark {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}