package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object for creating a new listing.
 * This class is used to transfer data from the client to the server when creating a new listing.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingCreationRequestDTO {
    private String title;
    private String description;
    private String categoryName;
    private String listingStatus;
    private double price;
    private double latitude;
    private double longitude;
}
