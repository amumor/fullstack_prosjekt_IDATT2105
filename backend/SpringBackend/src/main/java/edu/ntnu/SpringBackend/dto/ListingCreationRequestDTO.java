package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for creating a new listing.
 * This class is used to transfer data from the client to the server when creating a new listing.
 * It contains the necessary fields to represent a listing, including title, description, category name,
 * listing status, price, location (latitude and longitude), and images to delete.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingCreationRequestDTO {

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
  private ListingStatus listingStatus;

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
   * The list of image URLs to delete.
   */
  private List<String> imagesToDelete;
}
