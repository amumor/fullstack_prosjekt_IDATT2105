package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents an image associated with a listing.
 * Each image is linked to a specific listing and contains the URL of the image.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listing_images")
public class ListingImage {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "original_file_name", nullable = false)
  private String originalFileName;

  @ManyToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

}
