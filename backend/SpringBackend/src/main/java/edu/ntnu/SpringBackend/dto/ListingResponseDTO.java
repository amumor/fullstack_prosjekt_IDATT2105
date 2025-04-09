package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * DTO for a listing response.
 * This class is used to transfer data from the server to the client when retrieving a listing.
 * It contains the necessary fields to represent a listing, including seller information, title, description,
 * category name, listing status, price, location coordinates, timestamps, and image URLs.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingResponseDTO {

  /**
   * The unique identifier of the listing.
   */
  private UUID id;

  /**
   * The unique identifier of the seller associated with the listing.
   */
  private String sellerFirstName;

  /**
   * The unique identifier of the seller associated with the listing.
   */
  private String sellerLastName;

  /**
   * The title of the listing.
   */
  private String title;

  /**
   * The description of the listing.
   */
  private String description;

  /**
   * The name of the category to which the listing belongs.
   */
  private String categoryName;

  /**
   * The status of the listing.
   */
  private String listingStatus;

  /**
   * The price of the listing.
   */
  private double price;

  /**
   * The latitude of the location of the listing.
   */
  private double latitude;

  /**
   * The longitude of the location of the listing.
   */
  private double longitude;

  /**
   * The timestamp of when the listing was created.
   */
  private LocalDateTime createdAt;

  /**
   * The timestamp of when the listing was last edited.
   */
  private LocalDateTime lastEditedAt;

  /**
   * The list of image URLs associated with the listing.
   */
  private List<String> imageUrls;
}
