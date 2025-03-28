package edu.ntnu.SpringBackend.model;


import edu.ntnu.SpringBackend.model.enums.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

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
  private BigDecimal price;

  private Double latitude;

  private Double longitude;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Column()
  private String imageUrl;


  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
