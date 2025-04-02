package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UUID sellerId;
    private String title;
    private String description;
    private String categoryName;
    private String listingStatus;
    private double price;
    private double latitude;
    private double longitude;

    /**
     * Format: ISO 8601, Date and time when this listing was created.
     */
    private String createdAt;

    /**
     * Format ISO 8601, Date and time when this listing was last edited.
     */
    private String lastEditedAt;
    private UUID chatId;
}
