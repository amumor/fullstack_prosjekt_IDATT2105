package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for Listing responses.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingResponseDTO {
    private UUID id;
    private String sellerFirstName;
    private String sellerLastName;
    private String title;
    private String description;
    private String categoryName;
    private String listingStatus;
    private double price;
    private double latitude;
    private double longitude;
    private LocalDateTime createdAt;
    private LocalDateTime lastEditedAt;
    private List<String> imageUrls;
}
