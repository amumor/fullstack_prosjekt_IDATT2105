package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
    /**
     * Format: ISO 8601, Date and time when this listing was created.
     */
    private String createdAt;

    /**
     * Format ISO 8601, Date and time when this listing was last edited.
     */
    private String lastEditedAt;
}
