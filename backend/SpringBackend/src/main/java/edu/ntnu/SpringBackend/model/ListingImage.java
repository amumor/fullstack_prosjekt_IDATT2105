package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing an image associated with a listing.
 * This class is used to represent an image in the database.
 * It contains the necessary fields, an id, original file name, listing, and image URL, to represent an image.
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
@Table(name = "listing_images")
public class ListingImage {

  /**
   * The unique identifier of the image.
   * This field is automatically generated and cannot be null.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The original file name of the image.
   * This field cannot be null.
   */
  @Column(name = "original_file_name", nullable = false)
  private String originalFileName;

  /**
   * The listing associated with this image.
   * This field is a many-to-one relationship with the Listing class.
   */
  @ManyToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

  /**
   * The URL of the image.
   * This field cannot be null.
   */
  @Column(name = "image_url", nullable = false)
  private String imageUrl;

}
