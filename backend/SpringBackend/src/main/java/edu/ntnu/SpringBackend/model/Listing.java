package edu.ntnu.SpringBackend.model;

import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a listing.
 * This class is used to represent a listing in the database.
 * It contains the necessary fields, an id, title, description, category, status, price,
 * latitude, longitude, createdAt, lastEditedAt and images to represent a listing.
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
@Table(name = "listings")
public class Listing {

  /**
   * The unique identifier of the listing.
   * This field is automatically generated and cannot be null.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The user who created the listing.
   * Other words, the seller of the listing.
   * This field is a many-to-one relationship with the User class.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User seller;

  /**
   * The title of the listing.
   * This field cannot be null.
   */
  @Column(nullable = false)
  private String title;

  /**
   * The description of the listing.
   * This field cannot be null.
   */
  @Column(nullable = false)
  private String description;

  /**
   * The category of the listing.
   * This field is a many-to-one relationship with the Category class.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  /**
   * The status of the listing.
   * This field indicates whether the listing is active, sold, or inactive.
   * It is an enum type ListingStatus.
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ListingStatus status;

  /**
   * The price of the listing.
   * This field cannot be null.
   */
  @Column(nullable = false)
  private Double price;

  /**
   * The latitude of the listing.
   */
  private Double latitude;

  /**
   * The longitude of the listing.
   */
  private Double longitude;

  /**
   * The time when the listing was created.
   * This field is automatically set to the current time when the listing is created.
   * It cannot be null.
   */
  @Column(nullable = false)
  private LocalDateTime createdAt;

  /**
   * The time when the listing was last edited.
   * This field is automatically set to the current time when the listing is updated.
   */
  private LocalDateTime lastEditedAt;

  /**
   * The list of bookmarks associated with this listing.
   * This field is mapped by the "listing" field in the Bookmark class.
   * It is automatically managed by JPA and will be removed if the listing is deleted.
   */
  @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<ListingImage> images;

  /**
   * This method is called before the entity is persisted to the database.
   * It sets the createdAt field to the current time and initializes the status to ACTIVE if it is null.
   * It also initializes the images list to an empty list.
   */
  @PrePersist
  public void initializeDefaults() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
    if (status == null || status == ListingStatus.SOLD) {
      status = ListingStatus.ACTIVE;
    }
    images = new ArrayList<>();
  }

  /**
   * This method is called before the entity is updated in the database.
   * It sets the lastEditedAt field to the current time.
   */
  @PreUpdate
  protected void onUpdate() {
    lastEditedAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Listing{" +
            "id=" + id +
            ", title='" + title + '\'' +
            // other non-entity fields
            '}';
  }
}
