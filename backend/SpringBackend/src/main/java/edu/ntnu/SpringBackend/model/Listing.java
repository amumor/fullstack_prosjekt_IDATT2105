package edu.ntnu.SpringBackend.model;


import edu.ntnu.SpringBackend.model.enums.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User seller;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ListingStatus status;

  @Column(nullable = false)
  private Double price;

  private Double latitude;

  private Double longitude;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime lastEditedAt;

  @Column()
  private String imageUrl;


  @PrePersist
  public void initializeDefaults() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
    if (status == null || status == ListingStatus.SOLD) {
      status = ListingStatus.ACTIVE;
    }
  }


  @PreUpdate
  protected void onUpdate() {
    lastEditedAt = LocalDateTime.now();
  }
}
